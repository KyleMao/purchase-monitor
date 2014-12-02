package period

import types.AggreType
import types.PeriodType

/** A class that gets the weekly purchase stats.
  *
  * @author Zexi Mao
  */
class WeeklyStats extends PeriodStats {

  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getAllHistory: Array[Double] =
    super.getAllHistory(PeriodType.Week)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, PeriodType.Week)

}
