package distribution

/**
 * A class for getting the distribution of purchases of products.
 * 
 * @author Zexi Mao
 *
 */
final class ProductPurchaseDistribution extends PurchaseDistribution {
  
  private def getAggreStat(func: String): Float =
    super.getAggreStat(func, "product")
  
  def getAvg: Float = getAggreStat("avg")

  def getMin: Int =
    getAggreStat("min").asInstanceOf[Int]
  
  def getMax: Int =
    getAggreStat("max").asInstanceOf[Int]

  def getCnts: Array[Double] =
    super.getCnts("product")

  def getDistinctNum: Int =
    super.getDistinctNum("product")

  def getKmeansRange: (Array[Int], Array[Int]) =
    super.getKmeansRange("product")

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int]) =
    super.getKmeansRange("product", nCluster)

}