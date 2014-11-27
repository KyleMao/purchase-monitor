package config

import com.typesafe.config.ConfigFactory

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

  def getGraphDir =
    conf.getString("result.graphDir")

  def getNCluster = 
    conf.getInt("cluster.numCluster")
}