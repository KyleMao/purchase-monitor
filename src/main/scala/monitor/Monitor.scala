package monitor

import scala.collection.mutable.ArrayBuffer
import java.util.Date

import period.MinutelyStats
import period.DailyStats
import utils.ConfigReader
import utils.TimeManager

/**
 * An abstract class that implements the daily purchase monitor.
 * 
 * @author Zexi Mao
 *
 */
class Monitor {

  def isAbnormal(ds: String) = {
    val tm = new TimeManager
    val d = tm.getInputDate(ds)
    val (da, dstr) = dayPurchaseAbnormal(d)
    val (ma, mstr) = minutePurchaseAbnormal(d)
    if (!da && !ma)
      println("No anomaly detected.")
    else {
      if (da)
        println(dstr)
      if (ma)
        mstr.foreach(println)
    }
  }

  def dayPurchaseAbnormal(d: Date): (Boolean, String) = {
    val cr = new ConfigReader
    val (low, high) = cr.getDailyInter
    val ds = new DailyStats
    val pur = ds.getPurchase(d)
    if ((pur < low) || (pur > high))
      (true, s"Daily purchase number $pur is abnormal!")
    else
      (false, "")
  }

  def minutePurchaseAbnormal(d: Date): (Boolean, Array[String]) = {
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
