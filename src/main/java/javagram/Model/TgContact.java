/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.Model;

public class TgContact {
  String name, time, lastMessage;

  public TgContact() {
    name = String.valueOf(Math.random()*1000);
    time = String.valueOf((int)(Math.random()*9));
    lastMessage = String.valueOf(Math.random()*1000000);
  }

  @Override
  public String toString() {
    return name;
  }

  public String getName() {
    return name;
  }

  public String getTime() {
    return time;
  }

  public String getLastMessage() {
    return lastMessage;
  }
}
