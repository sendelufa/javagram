/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javagram.Configs;
import javagram.MainContract;
import javagram.Model.TLHandler;
import javagram.Model.TgContact;
import javagram.Model.TgMessage;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import org.javagram.response.object.UserContact;


public class PrChat implements MainContract.IPresenter {

  DefaultListModel<TgContact> contactsListModel;
  DefaultListModel<TgMessage> messagesListModel;

  //TelegramApiBridge
  ArrayList<UserContact> contactList = new ArrayList<>();
  private MainContract.IViewChat view;

  public PrChat(MainContract.IViewChat view) {
    this.view = view;
    //set view to frame
   /* this.view.setUserFullNameLabelTop(TLHandler.getInstance().getUserFullName());
      this.view.setUserPhotoTop(TLHandler.getInstance().getUserPhoto(),
     TLHandler.getInstance().getUserFirstName(), TLHandler.getInstance().getUserLastName());*/
  }

  public void getContactList() {
    try {
      contactList = TLHandler.getInstance().getContactList();

      contactsListModel = new DefaultListModel<>();
      contactsListModel.ensureCapacity(1);
      for (int i = 0; i < 100; i++) {
        contactsListModel.addElement(new TgContact("test"));
      /*
int i=0;

      for (UserContact contact:contactList){
        if (i > 10) break;
        BufferedImage photoPath = getUserPhoto(contact);
        contactsListModel.addElement(new TgContact(contact.getId(), contact.toString(), photoPath));
        i++;
      }*/}
      view.showContactList(contactsListModel);
    } catch (IOException e) {
      e.printStackTrace();
      view.showError("Ошибка при получении списка контактов! IOException getContactList()");
    }
  }

  public BufferedImage getUserPhoto(UserContact user){
    BufferedImage img = Configs.IMG_DEFAULT_USER_PHOTO_41_41;
    try {
      BufferedImage imgApi = ImageIO.read(new ByteArrayInputStream(user.getPhoto(true)));
      if (imgApi != null) {
        Image i = imgApi.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
        img = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = img.createGraphics();
        bGr.drawImage(i, 0, 0, null);
        bGr.dispose();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    catch (NullPointerException e){
      System.out.println("      -------     NullPointerException");
    }
    // Create a buffered image with transparency


    return img;
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

  public void logOut(){
    TLHandler.getInstance().logOut();
  }

}
