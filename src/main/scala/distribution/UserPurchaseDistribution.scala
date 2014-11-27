package distribution

/**
 * A class for getting the distribution of number purchases of users.
 * 
 * @author Zexi Mao
 *
 */
final class UserPurchaseDistribution extends PurchaseDistribution {
  
  private def getAggreStat(func: String): Float =
    super.getAggreStat(func, "user_id")
  
  def getAvg: Float = getAggreStat("avg")

  def getMin: Int =
    getAggreStat("min").asInstanceOf[Int]
  
  def getMax: Int =
    getAggreStat("max").asInstanceOf[Int]

  def getCnts: Array[Double] =
    super.getCnts("user_id")
  
  def getDistinctNum: Int =
    super.getDistinctNum("user_id")

  def getKmeansRange =
    super.getKmeansRange("user_id")

}