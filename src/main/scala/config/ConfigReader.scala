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
}