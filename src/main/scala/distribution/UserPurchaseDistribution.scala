package distribution

/**
 * A class for getting the distribution of number purchases of users.
 * 
 * @author Zexi Mao
 *
 */
final class UserPurchaseDistribution extends PurchaseDistribution {
  
  protected def getAggreStat(func: String): Float = {
    super.getAggreStat(func, "user_id")
  }
  
  def getMin = getAggreStat("min").asInstanceOf[Int]
  
  def getMax = getAggreStat("max").asInstanceOf[Int]
  
  def getAvg = getAggreStat("avg")
  
}