package period

import types.AggreType
import types.PeriodType

/**
 * A class that gets the hourly purchase stats.
 * 
 * @author Zexi Mao
 *
 */
class HourlyStats extends PeriodStats {

  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getAllHistory: Array[Double] =
    super.getAllHistory(PeriodType.Hour)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, PeriodType.Hour)

}
