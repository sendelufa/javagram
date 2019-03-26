/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.Model.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javagram.MainContract.IContact;
import javax.imageio.ImageIO;
import org.javagram.response.object.UserContact;


public class TgContact implements IContact {

  private String time, lastMessage;
  private BufferedImage photoSmall = null;
  private UserContact tlUserContact;

  public TgContact(UserContact contact) {
    this.tlUserContact = contact;
    //this.photoSmall = initSmallPhoto(defPhotoSmall);
    //this.photoSmall = defPhotoSmall;
    time = String.valueOf((int) (Math.random() * 9));
    lastMessage = String.valueOf(Math.random() * 1000000);
  }

  //for tests
  public TgContact(String test, UserContact tlUserContact) {
    if (test.equals("test")) {
      this.tlUserContact = tlUserContact;
      time = String.valueOf((int) (Math.random() * 9));
      lastMessage = String.valueOf(Math.random() * 1000000);
      //this.photoSmall = Configs.IMG_DEFAULT_USER_PHOTO_41_41;
    }
  }

  @Override
  public Object getApiContact() {
    return tlUserContact;
  }

  @Override
  public String getFullName() {
    return tlUserContact.toString();
  }

  @Override
  public String getFirstName() {
    return tlUserContact.getFirstName();
  }

  @Override
  public String getLastName() {
    return tlUserContact.getLastName();
  }

  @Override
  public String getInitiates() {
    return getFullNameInitiates(tlUserContact.getFirstName(), tlUserContact.getLastName());
  }

  private String getFullNameInitiates(String userFirstName, String userLastName) {
    if (userFirstName == null || userLastName == null || userFirstName.equals("")) {
      return "n/a error";
    }
    boolean hasLastName = (userLastName != null && !userLastName.equals(""));
    return hasLastName ? userFirstName.substring(0, 1) + userLastName.substring(0, 1)
        : userFirstName.substring(0, 1);
  }

  @Override
  public String getTime() {
    return time;
  }

  @Override
  public int getId() {
    return tlUserContact.getId();
  }

  @Override
  public String getLastMessage() {
    return lastMessage;
  }

  private BufferedImage initSmallPhoto(BufferedImage defaultPhoto) {
    if (photoSmall == null) {
      photoSmall = defaultPhoto;
      try {
        BufferedImage imgApi = ImageIO.read(new ByteArrayInputStream(tlUserContact.getPhoto(true)));
        if (imgApi != null) {
          Image i = imgApi.getScaledInstance(41, 41, Image.SCALE_SMOOTH);
          photoSmall = new BufferedImage(i.getWidth(null), i.getHeight(null),
              BufferedImage.TYPE_INT_ARGB);
          Graphics2D bGr = photoSmall.createGraphics();
          bGr.drawImage(i, 0, 0, null);
          bGr.dispose();
        }
      } catch (Exception e) {
        return photoSmall;
      }
    }
    return photoSmall;
  }

  @Override
  public BufferedImage getSmallPhoto() {
    return photoSmall;
  }


  @Override
  public BufferedImage getBigPhoto() {
    return null;
  }


  @Override
  public boolean isOnline() {
    return tlUserContact.isOnline();
  }

  @Override
  public void setLastMessage(String message) {
    lastMessage = message;
  }

  @Override
  public void setPhotoSmall(BufferedImage photoSmall) {
    this.photoSmall = photoSmall;
  }
}


