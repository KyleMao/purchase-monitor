package utils

import types.AggreType
import types.AmountType
import types.ObjType

/**
 * An object that holds the miscellaneous utility methods.
 * 
 * @author Zexi Mao
 *
 */
object Utils {
  
  def funcStr(agt: AggreType.Value) = agt match {
    case AggreType.Avg => "avg"
    case AggreType.Max => "max"
    case AggreType.Min => "min"
  }

  def groupStr(ot: ObjType.Value) = ot match {
    case ObjType.Product => "product"
    case ObjType.User => "user_id"
  }

  def sumStr(amt: AmountType.Value) = amt match {
    case AmountType.Purchase => "count(distinct purchase_id)"
    case AmountType.Quantity => "sum(quantity)"
  }

}