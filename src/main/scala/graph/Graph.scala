package graph

import breeze.linalg._
import breeze.plot._

import config.ConfigReader
import distribution.DistType
import distribution.DistributionFactory

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

  def clusterHist(t: DistType.Value) = {
    val gd = getGraphDir
    val dist = getDist(t)
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