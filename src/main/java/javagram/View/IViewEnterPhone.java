package javagram.View;


import javagram.Presenter.PrEnterPhone;

//view for window with phone input
public interface IViewEnterPhone extends IView {

  //show error
  void showPhoneFormatError(String strError);

  //main method init View
  void setPresenter(PrEnterPhone presenter);

  //set Phone, use if user go back from send code or registration screen
  void fillPhoneNumberTextField(String phone);
}
