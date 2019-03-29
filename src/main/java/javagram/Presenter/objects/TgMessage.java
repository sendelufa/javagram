/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.Presenter.objects;

public class TgMessage {
  private int id;
  private int fromId;
  private int toId;
  private boolean out;
  private boolean unread;
  private int date;
  private String message;

  public TgMessage(String message, int date, boolean isOut) {
    this.out = isOut;
    this.message = message;
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
