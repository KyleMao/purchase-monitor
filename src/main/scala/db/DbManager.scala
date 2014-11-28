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
    "jdbc:postgresql://localhost/" + cr.getDb,
    cr.getUser,
    cr.getPwd)
    
  def executeQuery(query: String) = {
    val st = db.createStatement
    st.executeQuery(query)
  }
}
