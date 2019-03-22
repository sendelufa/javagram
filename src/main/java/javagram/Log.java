/**
 * Project Javagram Created by Shibkov Konstantin on 22.03.2019.
 */
package javagram;

import java.util.logging.Logger;

public class Log  {
  private static Logger l = Logger.getLogger("1");
  public static void warning(String text){
    l.warning(text);
  }
  public static void info(String text){
    l.info(text);
  }
}
