/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.Model;

public class TgContact {
  String name, time, lastMessage;
  int id;
  static int count = 0;

  public TgContact() {
    id = count++;
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

  public int getId() {
    return id;
  }

  public String getLastMessage() {
    return lastMessage;
  }
}
