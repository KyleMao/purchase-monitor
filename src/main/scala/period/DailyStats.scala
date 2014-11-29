package period

import java.util.Date

import types.AggreType
import types.PeriodType

/**
 * A class that gets the daily purchase stats.
 * 
 * @author Zexi Mao
 *
 */
class DailyStats extends PeriodStats {

  def getPurchase(d: Date) =
    super.getPeriodPurchase(d, PeriodType.Day)

  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getAllHistory: Array[Double] =
    super.getAllHistory(PeriodType.Day)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, PeriodType.Day)

}
