package distribution

import types.AggreType
import types.AmountType
import types.ObjType

/**
 * An abstract class dealing with the distribution of product quantities
 * purchased.
 * 
 * @author Zexi Mao
 *
 */
abstract class QuantityDistribution extends Distribution {
  
  protected def getAggreStat(agt: AggreType.Value, ot: ObjType.Value): Float =
    super.getAggreStat(agt, ot, AmountType.Quantity)

  protected def getCnts(ot: ObjType.Value): Array[Double] =
    super.getCnts(ot, AmountType.Quantity)

  protected def getKmeansRange(ot: ObjType.Value): (Array[Int], Array[Int]) =
    super.getKmeansRange(ot, AmountType.Quantity)

  protected def getKmeansRange(ot: ObjType.Value, nCluster: Int):
    (Array[Int], Array[Int]) = {
    super.getKmeansRange(ot, AmountType.Quantity, nCluster)
  }

  protected def getWeeklyHistory(id: String, ot: ObjType.Value) =
    super.getWeeklyHistory(id, ot, AmountType.Quantity)
  
}
