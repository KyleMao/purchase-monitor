package distribution

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import nak.cluster._

import types.AggreType
import types.AmountType
import types.ObjType
import types.PeriodType
import utils.ConfigReader
import utils.DbManager
import utils.TimeManager
import utils.Utils

/** An abstract class that implements the general distribution methods.
  * 
  * @author Zexi Mao
  */
abstract class Distribution {
  private val cr = new ConfigReader
  private val tbl = cr.getTbl

  /* Returns the average, min or max of the amount of object.
   */
  protected def getAggreStat(agt: AggreType.Value, ot: ObjType.Value,
    amt: AmountType.Value): Float = {

    val func = Utils.funcStr(agt)
    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM $tbl GROUP BY $group)
      AS group_count;"""
    val res = DbManager.executeQuery(query)
    res.next()
    res.getFloat(func)
  }

  /* Returns the distinct number of objects (product or user) in the
   * database.
   */
  protected def getDistinctNum(ot: ObjType.Value): Int = {
    val group = Utils.groupStr(ot)
    val query = s"SELECT count(distinct $group) AS d_num FROM $tbl;"
    val res = DbManager.executeQuery(query)
    res.next()
    res.getInt("d_num")
  }

  /* Returns an Array for the amount of objects (products or users).
   */
  protected def getCnts(ot: ObjType.Value, amt: AmountType.Value)
    : Array[Double] = {

    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val query = s"""SELECT $group, $sum AS g_sum FROM $tbl
      GROUP BY $group ORDER BY g_sum DESC;"""
    val res = DbManager.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next()) {
      buf += res.getInt("g_sum")
    }
    buf.toArray
  }

  /* A wrapper method that does k-means clustering for objects (products or
   * users) and returns the range and number of samples in each cluster.
   */
  protected def getKmeansRange(ot: ObjType.Value, amt: AmountType.Value):
    (Array[Int], Array[Int]) = {

    val cr = new ConfigReader
    val nCluster = cr.getNCluster
    getKmeansRange(ot, amt, nCluster)
  }

  /* Actual k-means method.
   */
  protected def getKmeansRange(ot: ObjType.Value, amt: AmountType.Value,
    nCluster: Int): (Array[Int], Array[Int]) = {
    
    val cnts = getCnts(ot, amt)
    val fea = for (cnt <- cnts) yield Vector(cnt)
    val kmeans = new Kmeans[Vector[Double]](
      fea,
      Kmeans.euclideanDistance
    )
    val (dispsersion, cents) = kmeans.run(nCluster, 20)
    val (distances, preds) = kmeans.computeClusterMemberships(cents)
    val bins = Array.fill[Int](nCluster+1)(0)
    val sizes = Array.fill[Int](nCluster)(0)
    var bin = -1
    var prev = -1
    
    for ((pred,i) <- preds.view.zipWithIndex.reverseIterator) {
      if (pred != prev) {
        prev = pred
        bin += 1
        bins(bin) = cnts(i).asInstanceOf[Int]
      }
      sizes(bin) += 1
    }
    bins(bin+1) = cnts(0).asInstanceOf[Int]
    (bins, sizes)
  }

  /* Returns an array of number of weekly purhcases for each object (product
   * or user).
   */
  protected def getWeeklyHistory(id: String, ot: ObjType.Value,
    amt: AmountType.Value): Array[Double] = {

    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val tm = new TimeManager
    val wstr = Utils.periodStr(PeriodType.Week)
    val query = s"""SELECT $group, date_trunc('$wstr', time) AS week,
      $sum AS g_sum FROM $tbl WHERE $group='$id' GROUP BY $group, week
      ORDER BY week;"""
    val res = DbManager.executeQuery(query)
    val amount = ArrayBuffer.empty[Double]
    var preWeek = tm.getPrevWeek(tm.getDbStartTime)
    
    while (res.next()) {
      val week = tm.getTime(res.getString("week"))
      while (tm.weekDiff(preWeek, week) > 1) {
        amount += 0
        preWeek = tm.getNextWeek(preWeek)
      }
      amount += res.getInt("g_sum")
      preWeek = week
    }
    while (tm.weekDiff(preWeek, tm.getLatestPur) > 1) {
      amount += 0
      preWeek = tm.getNextWeek(preWeek)
    }
    amount.toArray
  }

  /* Check whether an object (product or user) is present in the database.
   */
  protected def isObjectPresent(id: String, ot: ObjType.Value): Boolean = {
    val group = Utils.groupStr(ot)
    val query = s"SELECT * FROM $tbl WHERE $group='$id'"
    val res = DbManager.executeQuery(query)
    if (res.next()) true else false
  }

  /** Returns the average number.
    */
  def getAvg: Float

  /** Returns the minimum number.
    */
  def getMin: Int

  /** Returns the maximum number.
    */
  def getMax: Int

  /** Returns the number of distinct objects (products or users) in database.
    */
  def getDistinctNum: Int

  /** Returns an array of the amount of objects (products or users).
    */
  def getCnts: Array[Double]

  /** Returns the range and number of samples in each cluster with the default
    * number of clusters.
    */
  def getKmeansRange: (Array[Int], Array[Int])

  /** Returns the range and number of samples in each cluster
    */
  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int])

  /** Returns an array of number of weekly purhcases for each object (product
    * or user).
    */
  def getWeeklyHistory(id: String): Array[Double]

  /** Checks whether an object (product or user) is in the database.
    */
  def isObjectPresent(id: String): Boolean

}
