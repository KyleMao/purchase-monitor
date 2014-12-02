package types

/** Types of distributions we would like to access.
  *
  * @author Zexi Mao
  */
object DistType extends Enumeration {
  type DistType = Value
  val ProdPur, ProdQuant, UserPur = Value
}
