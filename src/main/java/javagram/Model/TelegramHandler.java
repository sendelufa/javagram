/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;
import javagram.Configs;
import org.javagram.TelegramApiBridge;
import org.javagram.response.AuthAuthorization;
import org.javagram.response.AuthCheckedPhone;
import org.javagram.response.AuthSentCode;
import org.javagram.response.object.UserContact;
import org.telegram.api.TLImportedContact;
import org.telegram.api.TLInputContact;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.requests.TLRequestContactsImportContacts;
import org.telegram.tl.TLVector;

public class TelegramHandler {

  public static TelegramApiBridge bridge;
  private static Logger l = Logger.getLogger("1");
  private static String userPhone;
  private static boolean isPhoneRegistered = false;
  private static String userNameFull;
  private static TelegramApi tlApi;
  private static AuthAuthorization authorization;

  public static void clearApiBridge() {
    userPhone = "";
    isPhoneRegistered = false;
    userNameFull = "";
  }

  public static void connect() {
    if (bridge == null) {

      l.warning("bridge == null");

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
  }

  public static void checkPhoneRegistered(String ph) {
    try {
      AuthCheckedPhone checkedPhone = bridge.authCheckPhone(ph);
      userPhone = ph;
      isPhoneRegistered = checkedPhone.isRegistered();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isPhoneRegistered() {
    return isPhoneRegistered;
  }

  public static void sendCode() throws IOException {
    if (isPhoneRegistered) {
      //Отправляем проверочный код для пользователя с введеным номером телефона
      AuthSentCode sentCode = bridge.authSendCode(userPhone);
    }
  }

  public static void checkCode(String confirmCode) throws IOException {
    //проверка кода
    authorization = bridge.authSignIn(confirmCode);
    //получаем имя, фамилию юзера и записываем
    userNameFull = authorization.getUser().toString();
  }

  public static int getUserId(){
    return authorization.getUser().getId();
  }

  public static String getUserPhone() {
    return userPhone;
  }

  public static String getUserNameFull() {
    return userNameFull;
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
  private static void getTlApiReflection() {
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
