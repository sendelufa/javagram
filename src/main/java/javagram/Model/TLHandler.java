/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import javagram.Configs;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.AuthSentCode;
import org.telegram.api.engine.TelegramApi;

/**
 * Singleton Concurrency Pattern
 */

public class TLHandler {

  private static volatile TLHandler instance;
  private static Logger l = Logger.getLogger("1");
  private TelegramApiBridge bridge;
  private String userPhone;
  private boolean isPhoneRegistered = false;
  private String userNameFull;
  private TelegramApi tlApi;
  private AuthAuthorization authorization;

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
    synchronized (TLHandler.class) {
      if (instance == null) {
        instance = new TLHandler();
      }
    }
    return instance;
  }

  public void clearApiBridge() {
    userPhone = "";
    isPhoneRegistered = false;
    userNameFull = "";
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
    userNameFull = authorization.getUser().toString();
  }

  public int getUserId() {
    return authorization.getUser().getId();
  }

  public String getUserPhone() {
    return userPhone;
  }

  public String getUserNameFull() {
    return userNameFull;
  }

  public Image getUserPhoto() {
    Image img = Configs.IMG_DEFAULT_USER;
    try {
      byte[] userPhoto = authorization.getUser().getPhoto(true);
      if (userPhoto != null) {
        img = ImageIO.read(new ByteArrayInputStream(userPhoto));
      }
    } catch (IOException e) {
      e.printStackTrace();
      l.warning("НЕ ЗАГРУЗИЛАСЬ ФОТО ПОЛЬЗОВАТЕЛЯ!");
    }
    return img;
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
