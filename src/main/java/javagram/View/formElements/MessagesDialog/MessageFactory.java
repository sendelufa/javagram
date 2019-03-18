/**
 * Project Javagram Created by Shibkov Konstantin on 18.03.2019.
 */
package javagram.View.formElements.MessagesDialog;

public class MessageFactory {

  public static final int INCOMING = 1;
  public static final int OUTGOING = 2;

  public static IMessageItemDialog render(int messageType, String text, int date) {
    if (messageType == INCOMING) {
      return new MessageTextItemIncoming(text, date);
    } else if (messageType == OUTGOING) {
      return new MessageTextItemOutgoing(text, date);
    }
//TODO null - not good, maybe exception
    return null;
  }
}
