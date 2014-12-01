import graph.Graph
import monitor.Monitor
import types.DistType


/**
 * A command line interface for demoing the module.
 *
 * @author Zexi Mao
 *
 */
object Main  {
  val initMsg = "Welcome to the purchase monitor demo!"
  
  val mainOps = """Main Options:
    1 - Daily purchase monitor
    2 - View product purchase distribution
    3 - View user purchase distribution
    4 - View product purchase history
    q - Quit"""
  
  val prompt = "purchase-monitor> "
  
  val monitorOps = """Daily Purchase Monitor:
    YYYY-MM-DD - Check purchase status for a specific date
    m - Go to the main menu
    q - Quit"""
  
  val historyOps = """Product purchase history:
    product_id - View plot of purchase history by week
    m - Go to the main menu
    q - Quit"""

  val prodDistOps = """Product purchase distribution:
    number(3-9) - cluster the products n groups and show the group histogram
    p - View plot of distribution of number of purchases per product
    m - Go to the main menu
    q - Quit"""

  def main(args: Array[String]): Unit = {
    println(initMsg)
    var ok = true

    while (ok) {
      println(mainOps)
      print(prompt)
      val op = readLine()
      op match {
        case "1" => ok = monitor()
        //case "2" => ok = prodDist()
        case "4" => ok = history()
        case "q" => ok = false
        case _ => println("Unknown option!")
      } 
    }
  }

  def monitor() = {
    var ok = true
    var mok = true
    val mo = new Monitor

    while (mok) {
      println(monitorOps)
      print(prompt)
      val op = readLine()
      op match {
        case "m" => mok = false
        case "q" => {
          ok = false
          mok = false
        }
        case _ => {
          mo.isAbnormal(op)
        }
      }
    }
    ok
  }

  def history() = {
    var ok = true
    var hok = true
    val gh = new Graph

    while (hok) {
      println(historyOps)
      print(prompt)
      val op = readLine()
      op match {
        case "m" => hok = false
        case "q" => {
          ok = false
          hok = false
        }
        case _ => {
          gh.plotHistory(DistType.ProdPur, op)
        }
      }
    }
    ok
  }

  def prodDist() = {
    var ok = true
    var pok = true
    val gh = new Graph

    while (pok) {
      println(prodDistOps)
      print(prompt)
      val op = readLine()
      op match {
        case "p" => gh.plotDist(DistType.ProdPur)
        case "m" => pok = false
        case "q" => {
          ok = false
          pok = false
        }
        case _ => println("Unknown option!")
      }
    }
    ok
  }
  
}