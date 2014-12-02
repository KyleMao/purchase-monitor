package types

/** Types of amounts we want to investigate, namely, purchases
  * and product quantities.
  * 
  * @author Zexi Mao
  */
object AmountType extends Enumeration {
  type AmountType = Value
  val Purchase, Quantity = Value
}
