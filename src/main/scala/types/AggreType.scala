package types

/**
 * An enumeration of types of aggregate functions we would like to use.
 * 
 * @author Zexi Mao
 *
 */
object AggreType extends Enumeration {
  type AggreType = Value
  val Avg, Max, Min = Value
}
