/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.Model;

import java.awt.image.BufferedImage;
import javagram.Configs;

public class TgContact {
  String name, time, lastMessage;
  int id;
  static int count = 0;
  BufferedImage photo;

  public TgContact(int id, String name, BufferedImage photo) {
    this.id = id;
    this.name = name;
    this.photo = photo;
    time = String.valueOf((int)(Math.random()*9));
    lastMessage = String.valueOf(Math.random()*1000000);
  }
//for tests
  public TgContact(String test) {
    if (test.equals("test")) {
      id = count++;
      name = "имя " + id;
      time = String.valueOf((int) (Math.random() * 9));
      lastMessage = String.valueOf(Math.random() * 1000000);
      this.photo = Configs.IMG_DEFAULT_USER_PHOTO_41_41;
    }
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

  public BufferedImage getPhoto() {
    return photo;
  }
}
