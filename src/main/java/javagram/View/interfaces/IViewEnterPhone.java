package javagram.View.interfaces;


//view for window with phone input
public interface IViewEnterPhone extends IView {

  //set Phone, use if user go back from send code or registration screen
  void fillPhoneNumberTextField(String phone);

  //get View Send Code
  void callViewSendCode();

  //SHOW ERRORS AND INFO
  void showErrorPhoneEmpty();

  void showErrorPhoneFormat();

  void showInfoConnecting();
}
