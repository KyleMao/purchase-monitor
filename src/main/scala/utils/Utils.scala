package utils

import types.AggreType
import types.AmountType
import types.ObjType
import types.PeriodType

/** An object that holds the miscellaneous utility methods.
  *
  * @author Zexi Mao
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

  def periodStr(pt: PeriodType.Value) = pt match {
    case PeriodType.Min => "min"
    case PeriodType.Hour => "hour"
    case PeriodType.Day => "day"
    case PeriodType.Week => "week"
    case PeriodType.Month => "mon"
    case PeriodType.Year => "year"
  }

  def sumStr(amt: AmountType.Value) = amt match {
    case AmountType.Purchase => "count(distinct purchase_id)"
    case AmountType.Quantity => "sum(quantity)"
  }

}
