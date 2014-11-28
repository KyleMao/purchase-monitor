package types

/**
 * An enumeration of types of periods we are concerned about, from minute to year.
 * 
 * @author Zexi Mao
 *
 */
object PeriodType extends Enumeration {
  type PeriodType = Value
  val Min, Hour, Day, Week, Month, Year = Value
}
