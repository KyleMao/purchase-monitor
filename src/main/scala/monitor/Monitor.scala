package monitor

import scala.collection.mutable.ArrayBuffer
import java.util.Date

import period.MinutelyStats
import period.DailyStats
import utils.ConfigReader
import utils.TimeManager

/** A daily purchase monitor.
  *
  * @author Zexi Mao
  */
class Monitor {

  /** Checks whether the given date has abnormal purchases and prints out the
    * findings if any.
    */
  def isAbnormal(ds: String): Unit = {
    val tm = new TimeManager
    if (!tm.isDateValid(ds)) {
      println("Invalid date format!")
      return
    }

    val d = tm.getInputDate(ds)
    val begin = tm.getDbStartTime
    val end = tm.getLastDay
    
    if ((tm.dateDiff(begin, d) >= 0) && ((tm.dateDiff(d, end) >= 0))) {
      val (da, dstr) = dayPurchaseAbnormal(d)
      val (ma, mstr) = minutePurchaseAbnormal(d)
      if (!da && !ma) {
        println("No anomaly detected.")
      }
      else {
        if (da)
          println(dstr)
        if (ma)
          mstr.foreach(println)
      }
    } else {
      println("Date out of range!")
    }
  }

  /* Checks whether the daily purchase number is abnormal
   */
  private def dayPurchaseAbnormal(d: Date): (Boolean, String) = {
    val cr = new ConfigReader
    val (low, high) = cr.getDailyInter
    val ds = new DailyStats
    val pur = ds.getPurchase(d)
    if ((pur < low) || (pur > high))
      (true, s"Daily purchase number $pur is abnormal!")
    else
      (false, "")
  }

  /* Checks whether purchase number in each minute is abnormal.
   */
  private def minutePurchaseAbnormal(d: Date): (Boolean, Array[String]) = {
    val ms = new MinutelyStats
    val al = ms.getAbnormalMinute(d)
    val ra = ArrayBuffer.empty[String]
    if (al.isEmpty)
      (false, ra.toArray)
    else {
      for ((min, cnt) <- al)
        ra += s"Abnormal purchases at $min, number of purchases is $cnt"
      (true, ra.toArray)
    }
  }

}
