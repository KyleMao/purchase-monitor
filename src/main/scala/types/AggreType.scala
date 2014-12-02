package types

/** Types of aggregate functions.
  *
  * @author Zexi Mao
  */
object AggreType extends Enumeration {
  type AggreType = Value
  val Avg, Max, Min = Value
}
