package distribution
/**
 * An abstract class dealing with the distribution of product quantities
 * purchased.
 * 
 * @author Zexi Mao
 *
 */
abstract class QuantityDistribution extends Distribution {
  
  protected def getAggreStat(func: String, group: String): Float =
    super.getAggreStat(func, group, true)

  protected def getCnts(group: String): Array[Double] =
    super.getCnts(group, true)
  
  protected def getAggreStat(func: String): Float
  
}