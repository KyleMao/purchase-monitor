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

  protected def getPurchaseCnts(group: String) =
    super.getCnts(group, false)
  
  protected def getAggreStat(func: String): Float

}