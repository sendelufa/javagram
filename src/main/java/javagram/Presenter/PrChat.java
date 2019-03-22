/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import static java.lang.Thread.sleep;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import javagram.Model.objects.TgContact;
import javagram.Presenter.objects.TgMessage;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import org.javagram.response.object.UserContact;


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
  }

  public void getContactList() {

    Thread th = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          contactList = repository.getContactList();
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

  /*public BufferedImage getUserPhoto(IContact user) {
    BufferedImage img = Configs.IMG_DEFAULT_USER_PHOTO_41_41;
    try {
      BufferedImage imgApi = ImageIO.read(new ByteArrayInputStream(user.getPhoto(true)));
      if (imgApi != null) {
        Image i = imgApi.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
        img = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = img.createGraphics();
        bGr.drawImage(i, 0, 0, null);
        bGr.dispose();
        sleep(1000);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // Create a buffered image with transparency

    return img;
  }*/

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
