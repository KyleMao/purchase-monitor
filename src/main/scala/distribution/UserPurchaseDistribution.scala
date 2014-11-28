package distribution

/**
 * A class for getting the distribution of number purchases of users.
 * 
 * @author Zexi Mao
 *
 */
final class UserPurchaseDistribution extends PurchaseDistribution {
  
  def getAvg: Float = getAggreStat("avg")

  def getMin: Int =
    getAggreStat("min").asInstanceOf[Int]
  
  def getMax: Int =
    getAggreStat("max").asInstanceOf[Int]

  def getCnts: Array[Double] =
    super.getCnts("user_id")
  
  def getDistinctNum: Int =
    super.getDistinctNum("user_id")

  def getKmeansRange: (Array[Int], Array[Int]) =
    super.getKmeansRange("user_id")

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int]) =
    super.getKmeansRange("user_id", nCluster)

  def getWeeklyHistory(id: String): Array[Double] = 
    super.getWeeklyHistory(id, "user_id")

  private def getAggreStat(func: String): Float =
    super.getAggreStat(func, "user_id")

}
