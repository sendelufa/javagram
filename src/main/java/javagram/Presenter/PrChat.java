/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import static java.lang.Thread.sleep;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import javagram.Presenter.objects.TgMessage;
import javagram.View.formElements.ItemContactList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;


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


    Log.info("id user:" + repository.getUserId());

    getContactList();
  }

  public void showUserData() {
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

          int i = 0;

          for (IContact contact : contactList) {

            Log.info("add IContact contact " + contact.getId() + ":" + contact.getFullName());
/*            if (i++ > 100) {
              break;
            }*/
            contactsListModel.addElement(contact);
          }

          view.showContactList(contactsListModel);

        } catch (
            Exception e) {
          e.printStackTrace();
          view.showError("Ошибка при получении списка контактов! IOException getContactList()");
        }
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

          view.repaintContactList();

        }
      }
    }

    );

    threadGetPhotos.start();

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

  public void sendMessage() {
    try {
      repository.sendMessage();
    } catch (IOException e) {
      e.printStackTrace();
      view.showError("error sending message");
    }
  }

}
