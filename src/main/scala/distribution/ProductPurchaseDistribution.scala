package distribution

/**
 * A class for getting the distribution of purchases of products.
 * 
 * @author Zexi Mao
 *
 */
final class ProductPurchaseDistribution extends PurchaseDistribution {
  
  protected def getAggreStat(func: String): Float = {
    super.getAggreStat(func, "product")
  }
  
  def getMin = getAggreStat("min").asInstanceOf[Int]
  
  def getMax = getAggreStat("max").asInstanceOf[Int]
  
  def getAvg = getAggreStat("avg")
}