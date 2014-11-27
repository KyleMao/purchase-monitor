package time

import java.text.SimpleDateFormat
import java.util.Date

/**
 * A class for dealing with time such as parsing and comparing.
 * 
 * @author Zexi Mao
 *
 */
class TimeManager {

  def getTime(t: String) =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(t)

}