package db

import java.sql.DriverManager

import config.ConfigReader

/**
 * A database manager that deals with the database queries.
 * 
 * @author Zexi Mao
 *
 */
class DbManager {
  
  Class.forName("org.postgresql.Driver").newInstance
  private val cr = new ConfigReader
  private val db = DriverManager.getConnection(
    "jdbc:postgresql://localhost/" + cr.conf.getString("db.dbName"),
    cr.conf.getString("db.userName"),
    cr.conf.getString("db.password"))
    
  def executeQuery(query: String) = {
    val st = db.createStatement
    st.executeQuery(query)
  }
}