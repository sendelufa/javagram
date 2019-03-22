/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import java.util.ArrayList;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import javagram.Presenter.objects.TgMessage;
import javax.swing.DefaultListModel;


public class PrChat implements MainContract.IPresenter {

  //TelegramApiBridge
  ArrayList<IContact> contactList = new ArrayList<>();
  private Repository repository = new TelegramProdFactory().getModel();
  private MainContract.IViewChat view;
  private DefaultListModel<IContact> contactsListModel = new DefaultListModel<>();
  private DefaultListModel<TgMessage> messagesListModel;


  public PrChat(MainContract.IViewChat view) {
    this.view = view;
    //set view to frame
    this.view.setUserFullNameLabelTop(repository.getUserFullName());
    this.view.setUserPhotoTop(repository.getUserPhoto(),
        repository.getUserFirstName(), repository.getUserLastName());
    Log.info("id user:" + repository.getUserId());
  }

  public void addContact(){
   /* try {
      //int i = repository.addContact(new InputContact(80879, "9996622222", "New", "Contact"));

      //Log.info("addContact() id = " + i);
    } catch (IOException e) {
      e.printStackTrace();
    }*/
    try {
      repository.getCurrentUser();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getContactList() {
    Thread th = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          contactList = repository.getContactList();
          Log.info("contactList.size() = " + contactList.size());
/*
      contactsListModel = new DefaultListModel<>();
      contactsListModel.ensureCapacity(1);
      for (int i = 0; i < 100; i++) {
        contactsListModel.addElement(new TgContact("test"));
        }*/
          contactsListModel.clear();
          view.showContactList(contactsListModel);
          int i = 0;

          for (IContact contact : contactList) {
            Log.info("add IContact contact " + contact.getId() + ":" + contact.getFullName());
            if (i > 30) {
              break;
            }
            contactsListModel.addElement(contact);
            i++;
          }

        } catch (
            Exception e) {
          e.printStackTrace();
          view.showError("Ошибка при получении списка контактов! IOException getContactList()");
        }
      }

    });

    th.start();
  }

  public void clearContactListModel() {
    contactsListModel.clear();
  }

  public void getDialogMessages() {
    messagesListModel = new DefaultListModel<>();
    messagesListModel.ensureCapacity(20);
    for (int i = 0; i < 8; i++) {
      messagesListModel.addElement(new TgMessage(1552927340 - 60 * 2 * (8 - i), i));
    }
    view.showDialogMessages(messagesListModel);
  }

  public void logOut() {
    repository.logOut();
  }

}
