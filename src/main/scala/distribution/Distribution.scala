package distribution

import db.DbManager

/**
 * An abstract class that implements the general distribution methods.
 * 
 * @author Zexi Mao
 *
 */
abstract class Distribution {
  
  protected def getAggreStat(func: String, group: String, isQuant: Boolean): Float = {
    val dbm = new DbManager
    val sum = if (isQuant) "sum(quantity)" else "count(*)"
    val query = s"""SELECT $func(g_sum) FROM
      (SELECT $group, $sum AS g_sum FROM order_history GROUP BY $group)
      AS group_count;"""
    val res = dbm.executeQuery(query)
    res.next
    res.getFloat(func)
  }
  
  protected def getAggreStat(func: String, group: String): Float
}