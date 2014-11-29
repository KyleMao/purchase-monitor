package period

import scala.collection.mutable.ListBuffer
import java.util.Date

import types.AggreType
import types.PeriodType
import utils.ConfigReader
import utils.DbManager
import utils.TimeManager
import utils.Utils

/**
 * A class that gets the minutely purchase stats.
 * 
 * @author Zexi Mao
 *
 */
class MinutelyStats extends PeriodStats {

  def getAbnormalMinute(d: Date) = {
    val cr = new ConfigReader
    val dm = new DbManager
    val tm = new TimeManager
    val tbl = cr.getTbl
    val upper = cr.getMinuteUpper
    val mStr = Utils.periodStr(PeriodType.Min)
    val dStr = Utils.periodStr(PeriodType.Day)
    val date = tm.dateToStr(d)
    val query = s"""SELECT * FROM
      (SELECT date_trunc('$mStr', time) AS m, count(distinct purchase_id) AS c
      FROM $tbl GROUP BY m) AS t WHERE
      date_trunc('$dStr', m)='$date' AND c > $upper ORDER BY m;"""
    val res = dm.executeQuery(query)
    val ml = new ListBuffer[(String, Int)]
    if (res.next()) {
      val min = res.getString("m")
      val cnt = res.getInt("c")
      if (dupPurchase(min, cnt)) ml += ((min, cnt))
      while (res.next()) {
        val min = res.getString("m")
        val cnt = res.getInt("c")
        if (dupPurchase(min, cnt)) ml += ((min, cnt))
      }
    }
    ml.toList
  }

  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getAllHistory: Array[Double] =
    super.getAllHistory(PeriodType.Min)

  private def dupPurchase(min: String, cnt: Int) = {
    val cr = new ConfigReader
    val dm = new DbManager
    val tbl = cr.getTbl
    val mStr = Utils.periodStr(PeriodType.Min)
    val query = s"""SELECT count(*) AS c FROM
      (SELECT distinct * FROM
      (SELECT user_id, product, quantity, date_trunc('$mStr', time) AS m FROM $tbl)
      AS t where m='$min') as t2;"""
    val res = dm.executeQuery(query)
    res.next()
    val distinct = res.getInt("c")
    cnt != distinct
  }

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, PeriodType.Min)

}
