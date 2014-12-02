package distribution

import types.AggreType
import types.AmountType
import types.ObjType

/** An abstract class dealing with the distribution of purchases.
  *
  * @author Zexi Mao
  */
abstract class PurchaseDistribution extends Distribution {
  
  protected def getAggreStat(agt: AggreType.Value, ot: ObjType.Value): Float =
    super.getAggreStat(agt, ot, AmountType.Purchase)

  protected def getCnts(ot: ObjType.Value): Array[Double] =
    super.getCnts(ot, AmountType.Purchase)

  protected def getKmeansRange(ot: ObjType.Value): (Array[Int], Array[Int]) =
    super.getKmeansRange(ot, AmountType.Purchase)

  protected def getKmeansRange(ot: ObjType.Value, nCluster: Int):
    (Array[Int], Array[Int]) = {
    super.getKmeansRange(ot, AmountType.Purchase, nCluster)
  }

  protected def getWeeklyHistory(id: String, ot: ObjType.Value): Array[Double] =
    super.getWeeklyHistory(id, ot, AmountType.Purchase)

}
