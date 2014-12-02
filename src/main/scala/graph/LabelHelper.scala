package graph

import types.DistType
import types.PeriodType
import utils.Utils

/** An class that implements the graph utility methods.
  *
  * @author Zexi Mao
  */
class LabelHelper {

  def plotDistHelper(t: DistType.Value, gd: String) = t match {
    case DistType.ProdPur => {
      val xl = "Product"
      val yl = "# of purchases"
      val sn = gd + "product_purchase.png"
      (xl, yl, sn)
    }
    case DistType.ProdQuant => {
      val xl = "Product"
      val yl = "Quantities purchased"
      val sn = gd + "product_quantity.png"
      (xl, yl, sn)
    }
    case DistType.UserPur => {
      val xl = "User"
      val yl = "# of purchases"
      val sn = gd + "user_purchase.png"
      (xl, yl, sn)
    }
  }

  def plotHistoryHelper(t: DistType.Value, gd: String, id: String) = {
    val xl = "Week"
    t match {
      case DistType.ProdPur => {
        val yl = "# of purchases"
        val tt = "Product " + id
        val sn = gd + "product_" + id + "_purchase.png"
        (xl, yl, tt, sn)
      }
      case DistType.ProdQuant => {
        val yl = "Quantities purchased"
        val tt = "Product " + id
        val sn = gd + "product_" + id + "_quantity.png"
        (xl, yl, tt, sn)
      }
      case DistType.UserPur => {
        val yl = "# of purchases"
        val tt = "User " + id
        val sn = gd + "user_" + id + "_purchase.png"
        (xl, yl, tt, sn)
      }
    }
  }

  def drawHistHelper(t: DistType.Value, gd: String) = t match {
    case DistType.ProdPur => {
      val xl = "Product group"
      val yl = "# of products in group"
      val tt = "# purchases per product"
      val sn = gd + "product_purchase_clu.png"
      (xl, yl, tt, sn)
    }
    case DistType.ProdQuant => {
      val xl = "Product group"
      val yl = "# of products in group"
      val tt = "Quantities purchased per product"
      val sn = gd + "product_quantity_clu.png"
      (xl, yl, tt, sn)
    }
    case DistType.UserPur => {
      val xl = "User group"
      val yl = "# of users in group"
      val tt = "# purchases per user"
      val sn = gd + "user_purchase_clu.png"
      (xl, yl, tt, sn)
    }
  }

  def drawPHistHelper(t: PeriodType.Value, gd: String) = {
    val pStr = Utils.periodStr(t)
    val xl = "# of purchases"
    val yl = s"# of $pStr"
    val tt = "Purchase Distribution"
    val sn = gd + s"${pStr}_distribution.png"
    (xl, yl, tt, sn)
  }

  def plotPeriodHelper(t: PeriodType.Value, gd: String) = {
    val pStr = Utils.periodStr(t)
    val xl = pStr
    val yl = "# of purchases"
    val tt = "Purchase Number History"
    val sn = gd + s"${pStr}_history.png"
    (xl, yl, tt, sn)
  }

}