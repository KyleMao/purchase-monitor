package period

import scala.collection.mutable.ArrayBuffer
import java.util.Date

import types.AggreType
import types.AmountType
import types.PeriodType
import utils.ConfigReader
import utils.DbManager
import utils.TimeManager
import utils.Utils

/** An abstract class that gets the purchase stats of a period of time.
  *
  * @author Zexi Mao
  */
abstract class PeriodStats {
  private val cr = new ConfigReader
  private val tbl = cr.getTbl
  private val sstr = Utils.sumStr(AmountType.Purchase)

  /* Returns the number of purchases in a period.
   */
  protected def getPeriodPurchase(d: Date, pt: PeriodType.Value): Int = {
    val tm = new TimeManager
    val tStr = tm.dateToStr(d)
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT $sstr as p_num FROM $tbl WHERE
      date_trunc('$pStr', time)='$tStr'"""
    val res = DbManager.executeQuery(query)
    res.next()
    res.getInt("p_num")
  }

  /* Returns the whole purchase history on a period basis.
   */
  protected def getAllHistory(pt: PeriodType.Value): Array[Double] = {
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT date_trunc('$pStr', time) as period, $sstr as p_num
      FROM $tbl GROUP BY period ORDER BY period;"""
    val res = DbManager.executeQuery(query)
    val buf = ArrayBuffer.empty[Double]
    while (res.next()) {
      buf += res.getInt("p_num")
    }
    buf.toArray
  }

  /* Returns the average, min or max of the number of purchases on a period
   * basis.
   */
  protected def getAggreStat(agt: AggreType.Value, pt: PeriodType.Value): Float = {
    val func = Utils.funcStr(agt)
    val pStr = Utils.periodStr(pt)
    val query = s"""SELECT $func(p_num) FROM
      (SELECT date_trunc('$pStr', time) as period, $sstr as p_num FROM $tbl
      GROUP BY period) AS p;"""
    val res = DbManager.executeQuery(query)
    res.next()
    res.getFloat(func)
  }

  /** Returns the average number of purchases on a period basis.
    */
  def getAvg: Float

  /** Returns the maximum number of purchases on a period basis.
    */
  def getMax: Int

  /** Returns the minimum number of purchases on a period basis.
    */
  def getMin: Int

  /** Returns the whole purchase history on a period basis.
   */
  def getAllHistory: Array[Double]

}
