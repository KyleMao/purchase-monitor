import java.sql.DriverManager

import db.DbManager
import distribution.DistributionFactory
import distribution.DistType
import distribution.ProductQuantityDistribution
import distribution.UserPurchaseDistribution
import breeze.linalg._
import breeze.plot._
import config.ConfigReader

/**
 * @author Zexi Mao
 *
 */
object Test {

  Class.forName("org.postgresql.Driver").newInstance
  
  def main(args: Array[String]): Unit = {
    // val f = Figure()
    // val p = f.subplot(0)
    // val x = linspace(0, 10000)
    // p += plot(x, x :^ 2.0)
    // p += plot(x, x :^ 3.0, '.')
    // p.xlabel = "x axis"
    // p.ylabel = "y axis"
    // f.saveas("lines.png")
    // println("Hello")

    // val p2 = f.subplot(2,1,1)
    // val g = breeze.stats.distributions.Gaussian(0,1)
    // p2 += hist(g.sample(100000),100)
    // p2.title = "A normal distribution"
    // f.saveas("subplots.png")
    
    // var query = "SELECT COUNT(DISTINCT product) as C FROM order_history;"
    // var res = dbm.executeQuery(query)
    // res.next
    
    // val query = """SELECT user_id, count(*) AS g_sum FROM order_history
    //   GROUP BY user_id ORDER BY g_sum DESC;"""
    // val res = dbm.executeQuery(query)
    // var i = 0
    // println(counts.length)
    // while (res.next) {
    //   counts(i) = res.getInt("g_sum")
    //   // println(res.getInt("g_sum"))
    //   i += 1
    // }

    // val dbm = new DbManager
    // val dis = new UserPurchaseDistribution
    // val num = dis.getDistinctNum
    // val x = linspace(1, num, num)
    // val y = new DenseVector(dis.getPurchaseCnts)
    // // println(x.length)
    // // println(y.length)
    // val f = Figure()
    // val p = f.subplot(0)
    // p += plot(x, y)
    // p.xlabel = "Product"
    // p.ylabel = "# of purchases"

    // val cr = new ConfigReader
    // val gd = cr.getGraphDir
    // f.saveas(gd + "tmp.png")

    val df = new DistributionFactory
    val pqd =
      df.createDist(DistType.ProdQuant).asInstanceOf[ProductQuantityDistribution]
    val cnts = pqd.getQuantityCnts
    cnts.foreach(println)
    println(cnts.length)

  }

}