package distribution

import types.DistType

/** A factory that creates the distribution objects.
  *
  * @author Zexi Mao
  */
class DistributionFactory {
    
  def createDist(t: DistType.Value) = t match {
    case DistType.ProdPur  => new ProductPurchaseDistribution
    case DistType.ProdQuant => new ProductQuantityDistribution
    case DistType.UserPur  => new UserPurchaseDistribution
  }
  
}
