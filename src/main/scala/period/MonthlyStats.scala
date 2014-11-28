package period

import types.AggreType
import types.PeriodType

/**
 * A class that gets the monthly purchase stats.
 * 
 * @author Zexi Mao
 *
 */
class MonthlyStats extends PeriodStats {

  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getAllHistory: Array[Double] =
    super.getAllHistory(PeriodType.Month)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, PeriodType.Month)

}
