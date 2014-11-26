import java.sql.DriverManager

import db.DbManager
import distribution.ProductQuantityDistribution

/**
 * @author Zexi Mao
 *
 */
object Test {

  Class.forName("org.postgresql.Driver").newInstance
  
  def main(args: Array[String]): Unit = {
    //val dbm = new DbManager
    //val res = dbm.executeQuery("SELECT user_id FROM order_history LIMIT 10")
    //while (res.next) {
    //  println(res.getString("user_id"))
    //}
    val dis = new ProductQuantityDistribution
    println(dis.getMax)
  }

}