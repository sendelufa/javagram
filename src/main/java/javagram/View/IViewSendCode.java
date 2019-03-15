/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.View;

import javagram.Presenter.PrSendCode;

//view for window with phone input
public interface IViewSendCode extends IView {

  void setPresenter(PrSendCode presenter);

  void setPhoneNumber(String phone);
}
