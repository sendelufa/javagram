/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Presenter;

import javagram.Configs;
import javagram.Exceptions.PhoneFormatError;
import javagram.Model.TLHandler;
import javagram.View.IViewEnterPhone;
import javagram.View.ViewSendCode;
import javagram.WindowGUI.WindowHandler;

public class PrEnterPhone {

  IViewEnterPhone view;

  public PrEnterPhone(IViewEnterPhone view) {
    this.view = view;
    WindowHandler.setViewOnFrame(this.view);
  }

  public void checkPhone(String phone) {
    view.clearError();
    //clean input phone String
    String phone_clean = phone.trim().replaceAll("[^0-9]", "");

    //Check empty string
    if (phone_clean.isEmpty()){
      view.showError(Configs.ERR_PHONE_EMPTY);
      return;
    }

    //check phone number to valid length
    try {
      if (phone_clean.length() != Configs.SYS_PHONE_NUMBER_LENGTH) {

        view.showError(Configs.ERR_PHONE_FORMAT);
        throw new PhoneFormatError(Configs.ERR_PHONE_FORMAT);

      }
    } catch (PhoneFormatError ex) {
      ex.printStackTrace();
      return;
    }

    view.showInfo(Configs.INFO_CONNECT_TELEGRAM);
    view.showLoadingProcess();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        //check phone on telegram server
        TLHandler.getInstance().checkPhoneRegistered(phone_clean);
        if (TLHandler.getInstance().isPhoneRegistered()) {
          ViewSendCode view = new ViewSendCode();
          view.setPresenter(new PrSendCode(view));
        }
        view.hideLoadingProcess();
      }
    });
    thread.start();
  }
}
