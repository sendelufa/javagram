/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.View.interfaces;

//view for window with phone input
public interface IViewSendCode extends IView {
  void setPhoneNumber(String phone);
  //main panel with dialogs, contact list, messages
  void callViewChat();

  void callViewEnterPhone();

  void callViewEnterPhone(String phone);

  //SHOW ERRORS AND INFO
  void showErrorWrongCode();

  void showErrorUnknown();
}
