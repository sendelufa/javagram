package javagram.Presenter;

import javagram.Exceptions.PhoneFormatError;
import javagram.MainContract;
import javagram.Model.TelegramProdFactory;

/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */

public class PrEnterPhone implements MainContract.IPresenter {
  private MainContract.IViewEnterPhone view;
  private MainContract.Repository repository;

  private int requiredPhoneLength;

  public PrEnterPhone(MainContract.IViewEnterPhone view, int requiredPhoneLength)
  {
    this.view = view;
    this.requiredPhoneLength = requiredPhoneLength;
    //repository = TLRepositoryProd.getInstance();
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
      if (phone_clean.length() != requiredPhoneLength) {
        view.showErrorPhoneFormat();
        throw new PhoneFormatError("Phone Format Error");

      }
    } catch (PhoneFormatError ex) {
      ex.printStackTrace();
      return;
    }

    view.showInfoConnecting();
    view.showLoadingProcess();

    Thread thread = new Thread(() -> {
      repository = new TelegramProdFactory().getModel();
      //check phone on telegram server
      repository.checkPhoneRegistered(phone_clean);
      if (repository.isPhoneRegistered()) {
          view.callViewSendCode();
      }
      view.hideLoadingProcess();
    });
    thread.start();
  }
}
