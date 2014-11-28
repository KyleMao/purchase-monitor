package types

/**
 * An enumeration of types of objects we want to investigate, namely, users
 * and products.
 * 
 * @author Zexi Mao
 *
 */
object ObjType extends Enumeration {
  type ObjType = Value
  val Product, User = Value
}
