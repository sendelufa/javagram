/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Presenter;

import javagram.Exceptions.PhoneFormatError;
import javagram.Model.TLHandler;
import javagram.Presenter.interfaces.IPresenter;
import javagram.View.interfaces.IViewEnterPhone;


public class PrEnterPhone implements IPresenter {
  IViewEnterPhone view;

  public PrEnterPhone(IViewEnterPhone view) {
    this.view = view;
  }

  public void checkPhone(String phone) {
    view.clearError();
    //clean input phone String
    String phone_clean = phone.trim().replaceAll("[^0-9]", "");

    //Check empty string
    if (phone_clean.isEmpty()){
      view.showErrorPhoneEmpty();
      return;
    }

    //check phone number to valid length
    try {
      if (phone_clean.length() != 10) {
        view.showErrorPhoneFormat();
        throw new PhoneFormatError("Phone Format Error");

      }
    } catch (PhoneFormatError ex) {
      ex.printStackTrace();
      return;
    }

    view.showInfoConnecting();
    view.showLoadingProcess();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        //check phone on telegram server
        TLHandler.getInstance().checkPhoneRegistered(phone_clean);
        if (TLHandler.getInstance().isPhoneRegistered()) {
            view.callViewSendCode();
        }
        view.hideLoadingProcess();
      }
    });
    thread.start();
  }
}
