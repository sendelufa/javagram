/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Presenter;

import javagram.Configs;
import javagram.Exceptions.PhoneFormatError;
import javagram.Model.TelegramHandler;
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
      view.showPhoneFormatError(Configs.ERR_PHONE_EMPTY);
      return;
    }

    //check phone number to valid length
    try {
      if (phone_clean.length() != Configs.SYS_PHONE_NUMBER_LENGTH) {

        view.showPhoneFormatError(Configs.ERR_PHONE_FORMAT);
        throw new PhoneFormatError(Configs.ERR_PHONE_FORMAT);

      }
    } catch (PhoneFormatError ex) {
      ex.printStackTrace();
      return;
    }

    view.showError(Configs.INFO_CONNECT_TELEGRAM);
    //get connect Telegram Api

    view.showLoadingProcess();

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        //check phone on telegram server
        TelegramHandler.connect();
        //check phone on telegram server

        TelegramHandler.checkPhoneRegistered(phone_clean);
        if (TelegramHandler.isPhoneRegistered()) {
          ViewSendCode view = new ViewSendCode();
          view.setPresenter(new PrSendCode(view));
        }
        view.hideLoadingProcess();
      }
    });
    thread.start();


  }
}
