package monitor

import java.util.Date

import period.DailyStats
import utils.ConfigReader

/**
 * An abstract class that implements the daily purchase monitor.
 * 
 * @author Zexi Mao
 *
 */
class Monitor {

  def dayPurchaseAbnormal(d: Date) = {
    val cr = new ConfigReader
    val (low, high) = cr.getDailyInter
    val ds = new DailyStats
    val pur = ds.getPurchase(d)
    if ((pur < low) || (pur > high))
      (true, s"Daily purchase number $pur is abnormal!")
    else
      (false, "")
  }

}
