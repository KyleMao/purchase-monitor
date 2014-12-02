package distribution

import types.AggreType
import types.ObjType

/** A class for getting the distribution of purchases of products.
  *
  * @author Zexi Mao
  */
final class ProductPurchaseDistribution extends PurchaseDistribution {
  
  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Min).asInstanceOf[Int]

  def getCnts: Array[Double] =
    super.getCnts(ObjType.Product)

  def getDistinctNum: Int =
    super.getDistinctNum(ObjType.Product)

  def getKmeansRange: (Array[Int], Array[Int]) =
    super.getKmeansRange(ObjType.Product)

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int]) =
    super.getKmeansRange(ObjType.Product, nCluster)

  def getWeeklyHistory(id: String): Array[Double] =
    super.getWeeklyHistory(id, ObjType.Product)

  def isObjectPresent(id: String) =
    super.isObjectPresent(id, ObjType.Product)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, ObjType.Product)

}
