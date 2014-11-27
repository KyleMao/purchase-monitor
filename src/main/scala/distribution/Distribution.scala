package distribution

import scala.collection.mutable.ArrayBuffer

import config.ConfigReader
import db.DbManager

/**
 * An abstract class that implements the general distribution methods.
 * 
 * @author Zexi Mao
 *
 */
abstract class Distribution {
  
  private def sumType(isQuant: Boolean) =
    if (isQuant) "sum(quantity)" else "count(*)"

  protected def getAggreStat(func: String, group: String, isQuant: Boolean): Float = {
    val sum = sumType(isQuant)
    val cr = new ConfigReader
    val tbl = cr.getTbl
    val dbm = new DbManager
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM $tbl GROUP BY $group)
      AS group_count;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }

  protected def getDistinctNum(group: String) = {
    val cr = new ConfigReader
    val tbl = cr.getTbl
    val dbm = new DbManager
    val query = s"SELECT COUNT(DISTINCT $group) as d_num FROM $tbl;"
    val res = dbm.executeQuery(query)
    res.next
    res.getInt("d_num")
  }

  protected def getCnts(group: String, isQuant: Boolean) = {
    val sum = sumType(isQuant)
    val cr = new ConfigReader
    val tbl = cr.getTbl
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

  protected def getAggreStat(func: String, group: String): Float

  protected def getCnts(group: String): Array[Double]

  def getAvg: Float

  def getMin: Int

  def getMax: Int

  def getDistinctNum: Int

  def getCnts: Array[Double]

}