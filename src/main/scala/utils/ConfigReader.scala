package utils

import com.typesafe.config.ConfigFactory

import types.PeriodType

/**
 * A configuration reader that reads in configurations from a conf file.
 * 
 * @author Zexi Mao
 *
 */
class ConfigReader {
  val conf = ConfigFactory.load()

  def getDb = conf.getString("db.dbName")

  def getUser = conf.getString("db.userName")

  def getPwd = conf.getString("db.password")

  def getTbl = conf.getString("db.tblName")

  def getDbStartTime =
    conf.getString("db.startTime")

  def getGraphDir =
    conf.getString("result.graphDir")

  def getNCluster =
    conf.getInt("cluster.numCluster")

  def getMinuteUpper =
    conf.getInt("minuteNum.high")

  def getBinNum(t: PeriodType.Value) = {
    val pStr = Utils.periodStr(t)
    conf.getInt(s"binNum.$pStr")
  }

  def getDailyInter = {
    val cc = conf.getInt("dailyNum.confCoeff")
    val pref = s"dailyNum.interval_${cc}."
    (conf.getInt(s"${pref}low"), conf.getInt(s"${pref}high"))
  }

}
