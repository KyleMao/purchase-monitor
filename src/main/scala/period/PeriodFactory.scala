package period

import types.PeriodType

/**
 * A factory that creates the period stats objects
 * 
 * @author Zexi Mao
 *
 */
class PeriodFactory {
    
  def createPeriod(t: PeriodType.Value) = t match {
    case PeriodType.Min  => new MinutelyStats
    case PeriodType.Hour => new HourlyStats
    case PeriodType.Day  => new DailyStats
    case PeriodType.Week  => new WeeklyStats
    case PeriodType.Month => new MonthlyStats
    case PeriodType.Year  => new YearlyStats
  }
  
}
