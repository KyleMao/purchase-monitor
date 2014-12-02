package distribution

import types.AggreType
import types.ObjType

/** A class for getting the distribution of number purchases of users.
  *
  * @author Zexi Mao
  */
final class UserPurchaseDistribution extends PurchaseDistribution {
  
  def getAvg: Float =
    getAggreStat(AggreType.Avg)

  def getMax: Int =
    getAggreStat(AggreType.Max).asInstanceOf[Int]

  def getMin: Int =
    getAggreStat(AggreType.Min).asInstanceOf[Int]

  def getCnts: Array[Double] =
    super.getCnts(ObjType.User)
  
  def getDistinctNum: Int =
    super.getDistinctNum(ObjType.User)

  def getKmeansRange: (Array[Int], Array[Int]) =
    super.getKmeansRange(ObjType.User)

  def getKmeansRange(nCluster: Int): (Array[Int], Array[Int]) =
    super.getKmeansRange(ObjType.User, nCluster)

  def getWeeklyHistory(id: String): Array[Double] = 
    super.getWeeklyHistory(id, ObjType.User)

  def isObjectPresent(id: String) =
    super.isObjectPresent(id, ObjType.User)

  private def getAggreStat(agt: AggreType.Value): Float =
    super.getAggreStat(agt, ObjType.User)

}
