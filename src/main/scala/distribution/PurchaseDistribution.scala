package distribution
/**
 * An abstract class dealing with the distribution of purchases.
 * 
 * @author Zexi Mao
 *
 */
abstract class PurchaseDistribution extends Distribution {
  
  protected def getAggreStat(func: String, group: String): Float =
    super.getAggreStat(func, group, false)

  protected def getCnts(group: String): Array[Double] =
    super.getCnts(group, false)

  protected def getKmeansRange(group: String) =
    super.getKmeansRange(group, false)

}