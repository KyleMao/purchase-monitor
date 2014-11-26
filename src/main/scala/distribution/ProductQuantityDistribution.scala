package distribution

/**
 * A class for getting the distribution of products quantities purchased.
 * 
 * @author Zexi Mao
 *
 */
final class ProductQuantityDistribution extends QuantityDistribution {
  
  protected def getAggreStat(func: String): Float = {
    super.getAggreStat(func, "product")
  }
  
  def getMin = getAggreStat("min").asInstanceOf[Int]
  
  def getMax = getAggreStat("max").asInstanceOf[Int]
  
  def getAvg = getAggreStat("avg")
  
}