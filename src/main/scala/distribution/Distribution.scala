package distribution

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import nak.cluster._

import config.ConfigReader
import db.DbManager

/**
 * An abstract class that implements the general distribution methods.
 * 
 * @author Zexi Mao
 *
 */
abstract class Distribution {

  protected def getAggreStat(func: String, group: String, isQuant: Boolean): Float = {
    val sum = getSumType(isQuant)
    val tbl = getTbl
    val dbm = new DbManager
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM $tbl GROUP BY $group)
      AS group_count;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }

  protected def getDistinctNum(group: String) = {
    val tbl = getTbl
    val dbm = new DbManager
    val query = s"SELECT COUNT(DISTINCT $group) as d_num FROM $tbl;"
    val res = dbm.executeQuery(query)
    res.next
    res.getInt("d_num")
  }

  protected def getCnts(group: String, isQuant: Boolean) = {
    val sum = getSumType(isQuant)
    val tbl = getTbl
    val dbm = new DbManager
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

  private def getSumType(isQuant: Boolean) =
    if (isQuant) "sum(quantity)" else "count(*)"

  private def getTbl = {
    val cr = new ConfigReader
    cr.getTbl
  }

  def getAvg: Float

  def getMin: Int

  def getMax: Int

  def getDistinctNum: Int

  def getCnts: Array[Double]

  def getKmeansRange: (Array[Int], Array[Int])

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int])

}