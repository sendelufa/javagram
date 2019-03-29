/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.View.formElements.MessagesDialog;

public class MessageFactory {

  public enum Type {
    INCOMING,
    OUTGOING
  }

  public static IMessageItemDialog render(Type messageType, String text, int date) {
    if (messageType == Type.INCOMING) {
      return new MessageTextItemIncoming(text, date);
    } else if (messageType == Type.OUTGOING) {
      return new MessageTextItemOutgoing(text, date);
    }
//TODO null - not good, maybe exception
    return null;
  }
}
