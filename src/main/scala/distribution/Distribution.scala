package distribution

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import nak.cluster._

import types.AggreType
import types.AmountType
import types.ObjType
import utils.ConfigReader
import utils.DbManager
import utils.TimeManager
import utils.Utils

/**
 * An abstract class that implements the general distribution methods.
 * 
 * @author Zexi Mao
 *
 */
abstract class Distribution {
  private val dbm = new DbManager
  private val cr = new ConfigReader
  private val tbl = cr.getTbl

  protected def getAggreStat(agt: AggreType.Value, ot: ObjType.Value,
    amt: AmountType.Value): Float = {

    val func = Utils.funcStr(agt)
    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM $tbl GROUP BY $group)
      AS group_count;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }

  protected def getDistinctNum(ot: ObjType.Value) = {
    val group = Utils.groupStr(ot)
    val query = s"SELECT count(distinct $group) as d_num FROM $tbl;"
    val res = dbm.executeQuery(query)
    res.next
    res.getInt("d_num")
  }

  protected def getCnts(ot: ObjType.Value, amt: AmountType.Value) = {
    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val query = s"""SELECT $group, $sum AS g_sum FROM $tbl
      GROUP BY $group ORDER BY g_sum DESC;"""
    val res = dbm.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next) {
      buf += res.getInt("g_sum")
    }
    buf.toArray
  }

  protected def getKmeansRange(ot: ObjType.Value, amt: AmountType.Value):
    (Array[Int], Array[Int]) = {
    val cr = new ConfigReader
    val nCluster = cr.getNCluster
    getKmeansRange(ot, amt, nCluster)
  }

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

  protected def getWeeklyHistory(id: String, ot: ObjType.Value,
    amt: AmountType.Value): Array[Double] = {

    val group = Utils.groupStr(ot)
    val sum = Utils.sumStr(amt)
    val tm = new TimeManager
    val query = s"""SELECT $group, date_trunc('week', time) as week,
      $sum as g_sum FROM $tbl WHERE $group='$id' GROUP BY $group, week
      ORDER BY week;"""
    val res = dbm.executeQuery(query)
    val amount = ArrayBuffer.empty[Double]
    var preWeek = tm.getPrevWeek(tm.getDbStartTime)
    
    while (res.next) {
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

  def getAvg: Float

  def getMin: Int

  def getMax: Int

  def getDistinctNum: Int

  def getCnts: Array[Double]

  def getKmeansRange: (Array[Int], Array[Int])

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int])

  def getWeeklyHistory(id: String): Array[Double]

}
