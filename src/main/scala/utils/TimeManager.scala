package utils

import java.text.SimpleDateFormat
import java.util.Date

import types.PeriodType

/** A class for dealing with time such as parsing and comparing.
  *
  * @author Zexi Mao
  */
class TimeManager {

  def getTime(s: String) =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s)

  def isDateValid(s: String) = {
    try {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      sdf.setLenient(false)
      sdf.parse(s)
      true
    } catch {
      case e: Exception => false
    }
  }

  def getInputDate(s: String) =
    new SimpleDateFormat("yyyy-MM-dd").parse(s)

  def getDbStartTime = {
    val cr = new ConfigReader
    getTime(cr.getDbStartTime)
  }

  def getLatestPur = {
    val cr = new ConfigReader
    val tbl = cr.getTbl
    val query = s"SELECT max(time) as late FROM $tbl;"
    val res = DbManager.executeQuery(query)
    res.next()
    getTime(res.getString("late"))
  }

  def getLastDay = {
    val cr = new ConfigReader
    val tbl = cr.getTbl
    val dstr = Utils.periodStr(PeriodType.Day)
    val query = s"SELECT max(date_trunc('$dstr', time)) as late FROM $tbl;"
    val res = DbManager.executeQuery(query)
    res.next()
    getTime(res.getString("late"))
  }

  def getPrevWeek(d: Date) = getDayAfter(d, -7)

  def getNextWeek(d: Date) = getDayAfter(d, 7)

  def getDayAfter(d: Date, n: Int) =
    new Date(d.getTime + n * msPerDay)

  def dateToStr(d: Date) =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d)

  def weekDiff(d1: Date, d2: Date) =
    dateDiff(d1, d2) / 7

  def dateDiff(d1: Date, d2: Date) =
    (d2.getTime - d1.getTime) / msPerDay

  private def msPerDay = 24 * 60 * 60 * 1000

}
