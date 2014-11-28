package utils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * A class for dealing with time such as parsing and comparing.
 * 
 * @author Zexi Mao
 *
 */
class TimeManager {

  def getTime(t: String) =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t)

  def getDbStartTime = {
    val cr = new ConfigReader
    getTime(cr.getDbStartTime)
  }

  def getLatestPur = {
    val dbm = new DbManager
    val query = "SELECT max(time) as late FROM order_history;"
    val res = dbm.executeQuery(query)
    res.next
    getTime(res.getString("late"))
  }

  def getPrevWeek(d: Date) = getDayAfter(d, -7)

  def getNextWeek(d: Date) = getDayAfter(d, 7)

  def getDayAfter(d: Date, n: Int) =
    new Date(d.getTime + n * msPerDay)

  def weekDiff(d1: Date, d2: Date) =
    dateDiff(d1, d2) / 7

  def dateDiff(d1: Date, d2: Date) =
    (d2.getTime - d1.getTime) / msPerDay

  def msPerDay = 24 * 60 * 60 * 1000

}
