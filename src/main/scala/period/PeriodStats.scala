package period

import scala.collection.mutable.ArrayBuffer
import java.util.Date

import types.AggreType
import types.PeriodType
import utils.ConfigReader
import utils.DbManager
import utils.TimeManager
import utils.Utils

/**
 * An abstract class that gets the purchase stats of a period of time.
 * 
 * @author Zexi Mao
 *
 */
abstract class PeriodStats {
  private val dbm = new DbManager
  private val cr = new ConfigReader
  private val tbl = cr.getTbl

  protected def getPeriodPurchase(d: Date, pt: PeriodType.Value) = {
    val tm = new TimeManager
    val tStr = tm.dateToStr(d)
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT count(distinct purchase_id) as p_num FROM
      purchase_history WHERE date_trunc('$pStr', time)='$tStr'"""
    val res = dbm.executeQuery(query)
    res.next()
    res.getInt("p_num")
  }

  protected def getAllHistory(pt: PeriodType.Value): Array[Double] = {
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT date_trunc('$pStr', time) as period,
      count(distinct purchase_id) as p_num FROM $tbl GROUP BY period
      ORDER BY period;"""
    val res = dbm.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next()) {
      buf += res.getInt("p_num")
    }
    buf.toArray
  }

  protected def getAggreStat(agt: AggreType.Value, pt: PeriodType.Value): Float = {
    val func = Utils.funcStr(agt)
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT $func(p_num) FROM
      (SELECT date_trunc('$pStr', time) as period, count(distinct purchase_id)
      as p_num FROM $tbl GROUP BY period) AS daily;"""
    val res = dbm.executeQuery(query)
    res.next()
    res.getFloat(func)
  }

  def getAvg: Float

  def getMax: Int

  def getMin: Int

  def getAllHistory: Array[Double]

}
