package distribution

/**
 * A class for getting the distribution of number purchases of users.
 * 
 * @author Zexi Mao
 *
 */
final class UserPurchaseDistribution extends PurchaseDistribution {
  
  protected def getAggreStat(func: String): Float =
    super.getAggreStat(func, "user_id")
  
  def getAvg = getAggreStat("avg")

  def getMin =
    getAggreStat("min").asInstanceOf[Int]
  
  def getMax =
    getAggreStat("max").asInstanceOf[Int]

  def getDistinctNum =
    super.getDistinctNum("user_id")

  def getPurchaseCnts =
    super.getPurchaseCnts("user_id")
  
}