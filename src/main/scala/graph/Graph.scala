package graph

import scala.collection.mutable.ArrayBuffer

import breeze.linalg._
import breeze.plot._

import config.ConfigReader
import distribution.DistributionFactory
import distribution.DistType

/**
 * An class that implements the graph drawing methods.
 * 
 * @author Zexi Mao
 *
 */
class Graph {

  def plotDist(t: DistType.Value) = {
    val gd = getGraphDir
    val dist = getDist(t)
    val cnts = dist.getCnts
    val x = linspace(1, cnts.length, cnts.length)
    val y = new DenseVector(cnts)
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

  def drawClusterHist(t: DistType.Value, nCluster: Int) = {
    val gd = getGraphDir
    val dist = getDist(t)
    val (bins, sizes) = dist.getKmeansRange(nCluster)
    drawHist(t, bins, sizes)
  }

  def drawClusterHist(t: DistType.Value) = {
    val gd = getGraphDir
    val dist = getDist(t)
    val (bins, sizes) = dist.getKmeansRange
    drawHist(t, bins, sizes)
  }

  private def drawHist(t: DistType.Value, bins: Array[Int], sizes: Array[Int]) = {
    val gd = getGraphDir
    val f = Figure()
    val p = f.subplot(0)
    val buf = ArrayBuffer.empty[Int]
    var disc = new StringBuilder

    var png = ""
    t match {
      case DistType.ProdPur => {
        disc ++= "# purchases per product"
        png = gd + "product_purchase_clu.png"
      }
      case DistType.ProdQuant => {
        disc ++= "Quantities purchased per product"
        png = gd + "product_quantity_clu.png"
      }
      case DistType.UserPur => {
        disc ++= "# purchases per user"
        png = gd + "user_purchase_clu.png"
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

  private def getGraphDir() = {
    val cr = new ConfigReader
    cr.getGraphDir
  }
  
}
