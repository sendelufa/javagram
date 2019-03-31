/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import javagram.Presenter.objects.TgMessage;
import javax.swing.DefaultListModel;
import org.javagram.response.object.Message;

public class PrChat implements MainContract.IPresenter {

  //TelegramApiBridge
  private volatile ArrayList<IContact> contactList = new ArrayList<>();
  private Repository repository = new TelegramProdFactory().getModel();
  private MainContract.IViewChat view;
  private volatile DefaultListModel<IContact> contactsListModel = new DefaultListModel<>();
  private volatile DefaultListModel<TgMessage> messagesListModel;

  public PrChat(MainContract.IViewChat view) {
    this.view = view;
    //set view to frame
    showUserData();

    getContactList();
  }

  void showUserData() {
    this.view.setUserFullNameLabelTop(repository.getUserFullName());
    this.view.setUserPhotoTop(repository.getUserPhoto(),
        repository.getUserFirstName(), repository.getUserLastName());
  }

  public synchronized void getContactList() {

    Thread th = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          //get from Telegram Api
          contactList = repository.getContactList();
          Log.info("contactList.size() = " + contactList.size());

          //clear
          contactsListModel.clear();

          for (IContact contact : contactList) {
            Log.info("add IContact contact " + contact.getId() + ":" + contact.getFullName());
            contactsListModel.addElement(contact);
          }
          view.showContactList(contactsListModel);

        } catch (
            Exception e) {
          e.printStackTrace();
          view.showError("Ошибка при получении списка контактов! IOException getContactList()");
        }
        refreshUserPhotos();
      }

    });

    th.start();
  }

  public synchronized void refreshUserPhotos() {
    Thread threadGetPhotos = new Thread(new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < contactsListModel.getSize(); i++) {
          IContact c = contactsListModel.get(i);
          Log.info("start set small photo to contact " + c.getFullName() + "(" + c.getId() + ")");
          BufferedImage photoSmall = repository.getContactPhotoSmall(c);
          if (photoSmall != null) {
            c.setPhotoSmall(photoSmall);
            Log.info("smallphoto have setted for " + c.getFullName());
          }
          if (i % 5 == 0) {
            view.repaintContactList();
          }
        }
      }
    }
    );

    threadGetPhotos.start();

  }

  public void clearContactListModel() {
    contactsListModel.clear();
  }

  public void getDialogMessages(int userId) {
    messagesListModel = new DefaultListModel<>();
    ArrayList<Message> messages = null;
    try {
      messages = repository.getMessagesHistoryByUserId(userId);
      Collections.reverse(messages);
    } catch (IOException e) {
      e.printStackTrace();
      view.showError("Сообщения для чата " + userId + " не получены!");
    }
    for (Message message : messages) {
      messagesListModel
          .addElement(new TgMessage(message.getMessage(), message.getDate(), message.isOut()));
    }
    view.showDialogMessages(messagesListModel);
  }

  public void logOut() {
    repository.logOut();
  }

  public void sendMessage() {
    try {
      repository.sendMessage();
    } catch (IOException e) {
      e.printStackTrace();
      view.showError("error sending message");
    }
  }

}
