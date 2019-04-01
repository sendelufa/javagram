/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.Configs;
import javagram.MainContract;
import javagram.MainContract.IPresenter;
import javagram.MainContract.IViewAddContact;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;

public class PrAddContact implements IPresenter {

  PrChat presenterChat;
  private MainContract.IViewAddContact view;
  private Repository repository = new TelegramProdFactory().getModel();
  private String phone, firstName, lastName;

  public PrAddContact(IViewAddContact view, PrChat presenterChat) {
    this.presenterChat = presenterChat;
    this.view = view;
  }

  public void addContact(String phone, String firstName, String lastName) {
    //clean input phone String
    this.phone = phone.trim().replaceAll("[^0-9]", "");
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();

    if (isContactFieldsValid()) {
      view.showInfo("Поиск и добавление контакта...");
      try {
        int numberAddedContacts = repository.addContact(this.phone, this.firstName, this.lastName);
        if (numberAddedContacts == 1) {
          view.showInfo("Контакт успешно добавлен");
          repository.getContactListForceReload();
          view.closeModalView();
        } else if (numberAddedContacts == -1) {
          view.showError("Пользователь с таким именем уже существует!");
        } else if (numberAddedContacts == 0) {
          view.showErrorUserNotFound();
        } else {
          view.showError("Неизвестная ошибка!");
        }

        presenterChat.getContactList();

      } catch (IOException e) {
        e.printStackTrace();
        view.showError("Ошибка ответа от сервера Telegram!");
      }
    }
  }

  private boolean isContactFieldsValid() {

    // check phone
    if (phone.equals("")) {
      view.showErrorPhoneEmpty();
      return false;
    }
    //TODO 11 set to Configs.TL_REQUIRED_PHONE_LENGTH
    if (phone.length() != 11) {
      view.showErrorPhoneFormat();
      return false;
    }

    //check names
    if (firstName.equals("")) {
      if (lastName.equals("")) {
        view.showErrorEmptyFirstLast();
        return false;
      }
      view.showErrorEmptyFirst();
      return false;
    }
    return true;
  }
}
