package graph

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import breeze.plot._

import utils.ConfigReader
import distribution.DistributionFactory
import period.DailyStats
import types.DistType

/**
 * An class that implements the graph drawing methods.
 * 
 * @author Zexi Mao
 *
 */
class Graph {
  val cr = new ConfigReader
  val gd = cr.getGraphDir

  def plotDist(t: DistType.Value) = {
    val dist = getDist(t)
    val y = dist.getCnts
    val x = linspace(1, y.length, y.length)
    val f = Figure()
    val p = f.subplot(0)
    p += plot(x, y)

    t match {
      case DistType.ProdPur => {
        p.xlabel = "Product"
        p.ylabel = "# of purchases"
        f.saveas(gd + "product_purchase.png")
      }
      case DistType.ProdQuant => {
        p.xlabel = "Product"
        p.ylabel = "Quantities purchased"
        f.saveas(gd + "product_quantity.png")
      }
      case DistType.UserPur => {
        p.xlabel = "User"
        p.ylabel = "# of purchases"
        f.saveas(gd + "user_purchase.png")
      }
    }
  }

  def plotHistory(t: DistType.Value, id: String) = {
    val dist = getDist(t)
    val y = dist.getWeeklyHistory(id)
    val x = linspace(1, y.length, y.length)
    val f = Figure()
    val p = f.subplot(0)
    p += plot(x, y)

    p.xlabel = "Week"
    t match {
      case DistType.ProdPur => {
        p.ylabel = "# of purchases"
        p.title = "Product " + id
        f.saveas(gd + "product_" + id + "_purchase.png")
      }
      case DistType.ProdQuant => {
        p.ylabel = "Quantities purchased"
        p.title = "Product " + id
        f.saveas(gd + "product_" + id + "_quantity.png")
      }
      case DistType.UserPur => {
        p.ylabel = "# of purchases"
        p.title = "User " + id
        f.saveas(gd + "user_" + id + "_purchase.png")
      }
    }
  }

  def drawDailyHist() = {
    val ds = new DailyStats
    val dh = ds.getAllHistory
    val f = Figure()
    val p = f.subplot(0)
    p += hist(dh, 16)
    p.xlabel = "# of purchases"
    p.ylabel = "# of days"
    p.title = "Daily Purchase Distribution"
    f.saveas(gd + "daily_distribution.png")
  }

  def drawClusterHist(t: DistType.Value, nCluster: Int) = {
    val dist = getDist(t)
    val (bins, sizes) = dist.getKmeansRange(nCluster)
    drawHist(t, bins, sizes)
  }

  def drawClusterHist(t: DistType.Value) = {
    val dist = getDist(t)
    val (bins, sizes) = dist.getKmeansRange
    drawHist(t, bins, sizes)
  }

  private def drawHist(t: DistType.Value, bins: Array[Int], sizes: Array[Int]) = {
    val f = Figure()
    val p = f.subplot(0)
    val buf = ArrayBuffer.empty[Int]
    var disc = new StringBuilder

    var png = ""
    t match {
      case DistType.ProdPur => {
        disc ++= "# purchases per product"
        png = gd + "product_purchase_clu.png"
        p.xlabel = "Product group"
        p.ylabel = "# of products in group"
      }
      case DistType.ProdQuant => {
        disc ++= "Quantities purchased per product"
        png = gd + "product_quantity_clu.png"
        p.xlabel = "Product group"
        p.ylabel = "# of products in group"
      }
      case DistType.UserPur => {
        disc ++= "# purchases per user"
        png = gd + "user_purchase_clu.png"
        p.xlabel = "User group"
        p.ylabel = "# of users in group"
      }
    }

    for ((size, i) <- sizes.view.zipWithIndex) {
      buf ++= Array.fill[Int](size)(i+1)
      val low = bins(i)
      val high = bins(i+1) - 1
      disc ++= s"  $low-$high: $size"
    }
    p += hist(buf.toArray, sizes.length)
    p.title = disc.toString
    f.saveas(png)
  }
  
  private def getDist(t: DistType.Value) = {
    val df = new DistributionFactory
    df.createDist(t)
  }
  
}
