package period

import scala.collection.mutable.ArrayBuffer

import utils.ConfigReader
import utils.DbManager

/**
 * A class that gets the daily purchase stats.
 * 
 * @author Zexi Mao
 *
 */
class DailyStats {
  private val dbm = new DbManager
  private val cr = new ConfigReader
  private val tbl = cr.getTbl

  def getAvg = getAggreStat("avg")

  def getMin =
    getAggreStat("min").asInstanceOf[Int]

  def getMax =
    getAggreStat("max").asInstanceOf[Int]

  def getAllHistory = {
    val query = s"SELECT date(time) as day, count(*) as p_num FROM $tbl GROUP BY day;"
    val res = dbm.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next) {
      buf += res.getInt("p_num")
    }
    buf.toArray
  }

  private def getAggreStat(func: String) = {
    val query = s"""SELECT $func(p_num) FROM
      (SELECT date(time) as day, count(*) as p_num FROM $tbl GROUP BY day)
      AS daily;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }

}
