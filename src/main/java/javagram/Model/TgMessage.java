/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.Model;

public class TgMessage {
  private int id;
  private int fromId;
  private int toId;
  private boolean out;
  private boolean unread;
  private int date;
  private String message;

  private static String[] dummy = {"я себе урвал б/у hp z23i за 6к в прошлом году, норм вещь",
  "на некоторых вообще чуть ли не шлейф есть за курсором",
  "Вот вообще не встречал такого",
  "Ну вот 5 мс вроде норм нет?",
  "да, вполне, но сейчас делают матрицы с меньшим откликом",
  "на некоторых бюджетных тв видно",
  "5мс это 1/200 секунды же. Реально кому-то надо чаще?",
      "около 10 мс заметно в играх",
      "Ну у меня только 8600 бюджет"  };

  public TgMessage(int date, int idm){
    out = Math.random() > 0.5;
    message = dummy[idm];
    this.date = date;
  }

  public int getId() {
    return this.id;
  }

  public int getFromId() {
    return this.fromId;
  }

  public int getToId() {
    return this.toId;
  }

  public boolean isOut() {
    return this.out;
  }

  public boolean isUnread() {
    return this.unread;
  }

  public int getDate() {
    return this.date;
  }

  public String getMessage() {
    return this.message;
  }
}
