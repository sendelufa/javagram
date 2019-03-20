/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;
import javagram.Configs;
import javax.imageio.ImageIO;
import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.AuthSentCode;
import org.javagram.response.object.UserContact;
import org.telegram.api.engine.TelegramApi;

/**
 * Singleton Concurrency Pattern
 */

public class TLHandler implements IMessenger {

  private static volatile TLHandler instance;
  private static Logger l = Logger.getLogger("1");
  ArrayList<UserContact> contactList = new ArrayList<>();
  private TelegramApiBridge bridge;
  private String userPhone;
  private boolean isPhoneRegistered = false;
  private String userFullName;
  private String userFirstName;
  private String userLastName;
  private int userId;
  private TelegramApi tlApi;
  private AuthAuthorization authorization;

  @Override
  public void drive() {
    System.out.println("Drive");
  }

  private TLHandler() {
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

  public static TLHandler getInstance() {
    TLHandler localInstance = instance;

    if (localInstance == null){
      synchronized (TLHandler.class){
        localInstance = instance;
        if (localInstance == null){
          instance = localInstance = new TLHandler();
        }
      }
    }
    return localInstance;
  }

  public void clearApiBridge() {
    userPhone = "";
    isPhoneRegistered = false;
    userFullName = "";
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

  public void checkCode(String confirmCode) throws IOException {
    //проверка кода
    authorization = bridge.authSignIn(confirmCode);
    //получаем имя, фамилию юзера и записываем
    userFirstName = authorization.getUser().getFirstName();
    userLastName = authorization.getUser().getLastName();
    userFullName = authorization.getUser().toString();
    userId = authorization.getUser().getId();
  }

  public void getMessages() {

  }

  public ArrayList<UserContact> getContactList() throws IOException {
    if (contactList.isEmpty()) {
      contactList = bridge.contactsGetContacts();
    }
    return contactList;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public String getUserFullName() {
    return userFullName;
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
    Image img = null;
    try {
      byte[] userPhoto = authorization.getUser().getPhoto(true);
      //if user has no photo - set default gag
      if (userPhoto != null) {
        img = ImageIO.read(new ByteArrayInputStream(userPhoto));
      }
    } catch (IOException e) {
      e.printStackTrace();
      l.warning("НЕ ЗАГРУЗИЛАСЬ ФОТО ПОЛЬЗОВАТЕЛЯ!");
    }
    return img;
  }

  public void logOut(){
    try {
      bridge.authLogOut();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

 /* public static void getCurrentUser(){

    TLInputContact contact = new TLInputContact(0,"9996624443", "1", "1");
    TLVector<TLInputContact> v = new TLVector();
    v.add(contact);
    TLRequestContactsImportContacts ci = new TLRequestContactsImportContacts(v, true);
    TLImportedContacts ic = new TLImportedContacts();
    try {
      ic = (TLImportedContacts) tlApi.doRpcCall(ci);
    } catch (IOException e) {
      e.printStackTrace();
    }

    TLVector<TLImportedContact> listIC = ic.getImported();

    System.out.println(listIC.isEmpty());
  }*/

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
