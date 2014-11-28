package distribution

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import nak.cluster._

import config.ConfigReader
import db.DbManager
import time.TimeManager

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

  protected def getAggreStat(func: String, group: String, isQuant: Boolean): Float = {
    val sum = getSumType(isQuant)
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM $tbl GROUP BY $group)
      AS group_count;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }

  protected def getDistinctNum(group: String) = {
    val query = s"SELECT COUNT(DISTINCT $group) as d_num FROM $tbl;"
    val res = dbm.executeQuery(query)
    res.next
    res.getInt("d_num")
  }

  protected def getCnts(group: String, isQuant: Boolean) = {
    val sum = getSumType(isQuant)
    val query = s"""SELECT $group, $sum AS g_sum FROM $tbl
      GROUP BY $group ORDER BY g_sum DESC;"""
    val res = dbm.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next) {
      buf += res.getInt("g_sum")
    }
    buf.toArray
  }

  protected def getKmeansRange(group: String, isQuant: Boolean):
    (Array[Int], Array[Int]) = {
    val cr = new ConfigReader
    val nCluster = cr.getNCluster
    getKmeansRange(group, isQuant, nCluster)
  }

  protected def getKmeansRange(group: String, isQuant: Boolean, nCluster: Int):
    (Array[Int], Array[Int]) = {
    
    val cnts = getCnts(group, isQuant)
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

  protected def getWeeklyHistory(id: String, group: String, isQuant: Boolean):
    Array[Double] = {

    val sum = getSumType(isQuant)
    val tm = new TimeManager
    val query = s"""SELECT $group, date_trunc('week', time) as week,
      $sum as g_sum FROM order_history WHERE $group='$id' GROUP BY $group, week
      ORDER BY week;"""
    val res = dbm.executeQuery(query)
    val measure = ArrayBuffer.empty[Double]
    var preWeek = tm.getDayAfter(tm.getDbStartTime, -7)
    
    while (res.next) {
      val week = tm.getTime(res.getString("week"))
      while (tm.dateDiff(preWeek, week) > 7) {
        measure += 0
        preWeek = tm.getDayAfter(preWeek, 7)
      }
      measure += res.getInt("g_sum")
      preWeek = week
    }
    while (tm.dateDiff(preWeek, tm.getLatestPur) > 7) {
      measure += 0
      preWeek = tm.getDayAfter(preWeek, 7)
    }
    measure.toArray
  }

  private def getSumType(isQuant: Boolean) =
    if (isQuant) "sum(quantity)" else "count(*)"

  def getAvg: Float

  def getMin: Int

  def getMax: Int

  def getDistinctNum: Int

  def getCnts: Array[Double]

  def getKmeansRange: (Array[Int], Array[Int])

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int])

  def getWeeklyHistory(id: String): Array[Double]

}
