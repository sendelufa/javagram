/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.Presenter.objects;

import javagram.MainContract.IMessage;

public class TgMessage implements IMessage {

  private int id;
  private int fromId;
  private int toId;
  private boolean out;
  private boolean isUnread;
  private int date;
  private String message;

  public TgMessage(int id, int fromId, int toId, String message, int date, boolean isOut,
      boolean isUnread) {
    this.out = isOut;
    this.message = message;
    this.date = date;
    this.id = id;
    this.fromId = fromId;
    this.toId = toId;
    this.isUnread = isUnread;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public int getFromId() {
    return this.fromId;
  }

  @Override
  public int getToId() {
    return this.toId;
  }

  @Override
  public boolean isOut() {
    return this.out;
  }

  @Override
  public boolean isUnread() {
    return this.isUnread;
  }

  @Override
  public int getDate() {
    return this.date;
  }

  @Override
  public String getMessageText() {
    if (this.message == null) {
      return "нет переписки";
    }
    return this.message;
  }
}
