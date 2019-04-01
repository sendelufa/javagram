/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import static java.lang.Thread.sleep;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.Model.objects.InputContact;
import javagram.Model.objects.TgContact;
import javax.imageio.ImageIO;
import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.AuthSentCode;
import org.javagram.response.object.Dialog;
import org.javagram.response.object.Message;
import org.javagram.response.object.UserContact;
import org.telegram.api.TLAbsMessage;
import org.telegram.api.TLImportedContact;
import org.telegram.api.TLInputContact;
import org.telegram.api.TLInputPeerContact;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.messages.TLAbsMessages;
import org.telegram.api.requests.TLRequestContactsImportContacts;
import org.telegram.api.requests.TLRequestMessagesGetHistory;
import org.telegram.tl.TLVector;

/**
 * Singleton Concurrency Pattern
 */

public class TLRepositoryProd extends TLAbsRepository implements MainContract.Repository {


  private static Logger l = Logger.getLogger("1");
  private ArrayList<UserContact> contactList = new ArrayList<>();
  private ArrayList<IContact> contactListJavaGram = new ArrayList<>();
  private TelegramApiBridge bridge = null;

  private TelegramApi tlApi;
  private AuthAuthorization authorization = null;

  //смс код прошедший проверку
  private String smsCodeChecked = "";

  private TLRepositoryProd() {
    //Подключаемся к API телеграмм
    try {
      bridge = new TelegramApiBridge(Configs.TL_SERVER, Configs.TL_APP_ID, Configs.TL_APP_HASH);
      //получаем доступ и ссылку на параметр api TelegramApiBridge
      getTlApiReflection();

      l.warning("tlApi.getState()" + tlApi.getState());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static TLRepositoryProd getInstance() {
    TLRepositoryProd localInstance = instance;

    if (localInstance == null) {
      synchronized (TLRepositoryProd.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TLRepositoryProd();
        }
      }
    }
    return localInstance;
  }

  public void clearApiBridge() {
    userPhone = "";
    isPhoneRegistered = false;
  }

  public void checkPhoneRegistered(String ph) {
    try {
      AuthCheckedPhone checkedPhone = bridge.authCheckPhone(ph);
      userPhone = ph;
      isPhoneRegistered = checkedPhone.isRegistered();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isPhoneRegistered() {
    return isPhoneRegistered;
  }

  public void sendCode() throws IOException {
    if (isPhoneRegistered) {
      //Отправляем проверочный код для пользователя с введеным номером телефона
      AuthSentCode sentCode = bridge.authSendCode(userPhone);
    }
  }

  public void signIn(String confirmCode) throws IOException {
    //проверка кода
    smsCodeChecked = confirmCode;
    authorization = bridge.authSignIn(confirmCode);
    //получаем имя, фамилию юзера и записываем
    userFirstName = authorization.getUser().getFirstName();
    userLastName = authorization.getUser().getLastName();
    userId = authorization.getUser().getId();
  }

  @Override
  public synchronized ArrayList<IContact> getContactList(boolean forceReload) throws IOException {
    Log.info("Start getContactList");
    if (contactList.isEmpty() || contactListJavaGram.isEmpty() || forceReload) {
      Log.info("Start getContactList - get bridge.contactsGetContacts()");
      contactList = bridge.contactsGetContacts();
      contactListJavaGram.clear();
      for (UserContact user : contactList) {
        contactListJavaGram.add(new TgContact(user));
      }
    }
    return contactListJavaGram;
  }

  @Override
  public ArrayList<IContact> getContactList() throws IOException {
    return getContactList(false);
  }

  @Override
  public ArrayList<IContact> getContactListForceReload() throws IOException {
    return getContactList(true);
  }

  @Override
  public synchronized BufferedImage getContactPhotoSmall(IContact contact) {
    BufferedImage photoSmall = null;
    if (contactListJavaGram.contains(contact)) {
      //get native UserContact for use API methods
      UserContact tlContact = (UserContact) contact.getApiContact();
      //try to find img in cache folder with user photos
      File filePhotoSmall = new File(Configs.PATH_USER_PHOTO + tlContact.getId() + "-small.jpg");
      if (filePhotoSmall.exists() && filePhotoSmall.isFile()) {
        try {
          photoSmall = ImageIO.read(filePhotoSmall);
          //if empty img - user have no photo
          if (photoSmall.getHeight() == 2 & photoSmall.getWidth() == 2) {
            return null;
          }
          return photoSmall;
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      //get contact from Telegram API
      try {
        photoSmall = ImageIO.read(new ByteArrayInputStream(tlContact.getPhoto(true)));
        //write cache
        File outputFile = new File(Configs.PATH_USER_PHOTO + contact.getId() + "-small.jpg");
        try {
          ImageIO.write(photoSmall, "jpg", outputFile);
          Log.info("file saved!");
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      } catch (NullPointerException e) {
        Log.warning(
            "NullPointerException Repository getContactPhotoSmall()" + contact.getFullName());
        BufferedImage nullImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        File outputFile = new File(Configs.PATH_USER_PHOTO + contact.getId() + "-small.jpg");
        try {
          ImageIO.write(nullImage, "jpg", outputFile);
        } catch (IOException e1) {
          Log.warning(
              "IOException ошибка записи пустой авы для " + contact.getFullName());
        }
        return null;
      }
      //after every request do sleep more than 1 second or you can get ban for 12-24 hours (FLOOD_WAIT)
      finally {
        try {
          sleep(Configs.API_DELAY_REQUEST);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    return photoSmall;
  }

  @Override
  public synchronized boolean editUserProfile(BufferedImage newPhoto, String firstName,
      String lastName) {
    try {
      bridge.accountUpdateProfile(firstName, lastName);
      updateUserPhoto(newPhoto);
      this.userFirstName = firstName;
      this.userLastName = lastName;
      userPhotoSmall = newPhoto;


    } catch (IOException e) {
      e.printStackTrace();
      Log.warning("repository editUserProfile - IOException");
      return false;
    }
    return true;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public String getUserFullName() {
    return (userFirstName + " " + userLastName).trim();
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public String getUserLastName() {
    return userLastName;
  }

  public int getUserId() {
    return userId;
  }

  public Image getUserPhoto() {
    if (userPhotoSmall == null) {
      try {
        byte[] userPhoto = authorization.getUser().getPhoto(true);
        //if user has no photo - set default gag
        if (userPhoto != null) {
          userPhotoSmall = ImageIO.read(new ByteArrayInputStream(userPhoto));
        } else {
          File filePhotoSmall = new File(Configs.PATH_USER_PHOTO + "_user-small.jpg");
          if (filePhotoSmall.exists() && filePhotoSmall.isFile()) {
            try {
              userPhotoSmall = ImageIO.read(filePhotoSmall);
            } catch (IOException e) {
              e.printStackTrace();
              l.warning("ошибка чтения фото профиля с диска!");
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
        l.warning("НЕ ЗАГРУЗИЛАСЬ ФОТО ПОЛЬЗОВАТЕЛЯ!");
      } catch (NullPointerException e) {
        e.printStackTrace();
        l.warning("Ошибка загрузки изображения!");
      }
    }
    return userPhotoSmall;
  }

  public String getSmsCodeChecked() {
    return smsCodeChecked;
  }

  public void logOut() {
    try {
      if (authorization != null && bridge != null) {
        bridge.authLogOut();
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void signUp(String smsCode, String firstName, String lastName) throws IOException {
    AuthAuthorization auth = bridge.authSignUp(smsCode, firstName, lastName);
  }

  //добавление юзера в контакт лист
  //возврат количество добавленных пользователей
  //при удачном добавлении = 1
  //если контакт уже есть в списке = -1
  @Override
  public int addContact(String phone, String firstname, String lastName) throws IOException {

    //check if contact already exist
    for (UserContact userContact : contactList) {
      if (userContact.getPhone().equals(phone)) {
        return -1;
      }
    }

    InputContact contact = new InputContact(0, phone, firstname, lastName);
    TLVector<TLInputContact> v = new TLVector();
    v.add(contact.createTLInputContact());
    TLRequestContactsImportContacts ci = new TLRequestContactsImportContacts(v, false);
    TLImportedContacts ic = this.tlApi.doRpcCall(ci);
    Log.info(ic.getUsers().size() + " ic.getUsers()");

    TLVector<TLImportedContact> listIC = ic.getImported();
    for (TLImportedContact c : listIC) {
      Log.info("TLImportedContact" + c.getUserId());
    }
    System.out.println(listIC.isEmpty());
    return listIC.size();
  }

  private void updateUserPhoto(BufferedImage photo) throws IOException {
    File outputFile = new File(Configs.PATH_USER_PHOTO + "_user-small.jpg");
    ImageIO.write(photo, "jpg", outputFile);
  }

  @Override
  public void sendMessage(int contactId, String text, int randomId) throws IOException {
    bridge.messagesSendMessage(contactId, text, randomId);
  }

  //list of messages by contact id
  @Override
  public ArrayList<Message> getMessagesHistoryByUserId(int userId) throws IOException {
    ArrayList<Message> messages = getMessagesHistory(userId, 0, Integer.MAX_VALUE, 50);
    return messages;
  }

  @Override
  public ArrayList<Message> messagesGetDialogs(int offset, int maxId, int limit)
      throws IOException {
    ArrayList<Integer> messageIds = new ArrayList<>();
    ArrayList<Dialog> dialogs = bridge.messagesGetDialogs(0, Integer.MAX_VALUE, 500);
    for (Dialog d : dialogs) {
      messageIds.add(d.getTopMessage());
    }
    ArrayList<Message> messages = bridge.messagesGetMessages(messageIds);        //работает
    return messages;
  }

  private ArrayList<Message> getMessagesHistory(int userId, int offset, int maxId, int limit)
      throws IOException {
    TLRequestMessagesGetHistory request = new TLRequestMessagesGetHistory(
        new TLInputPeerContact(userId), offset, maxId, limit);
    TLVector<TLAbsMessage> tlAbsMessages = ((TLAbsMessages) tlApi.doRpcCall(request)).getMessages();
    ArrayList<Message> messages = new ArrayList();
    Iterator var7 = tlAbsMessages.iterator();

    while (var7.hasNext()) {
      TLAbsMessage tlMessage = (TLAbsMessage) var7.next();
      messages.add(new Message(tlMessage));
    }
    return messages;
  }

  //REFLECTION API
  //получаем доступ к приватной переменной api из TelegramApiBridge
  private void getTlApiReflection() {
    Field fieldApi;
    try {
      //попытка получить параметр api класса TelegramApiBridge
      fieldApi = bridge.getClass().getDeclaredField("api");
      //позволяем получить доступ к полю для работы с ним в обход private
      fieldApi.setAccessible(true);
      //передаем ссылку на приватный параметр api в текущий параметр tlApi
      tlApi = (TelegramApi) fieldApi.get(bridge);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
