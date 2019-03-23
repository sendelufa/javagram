/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import static java.lang.Thread.sleep;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import org.javagram.response.object.UserContact;
import org.telegram.api.TLImportedContact;
import org.telegram.api.TLInputContact;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.requests.TLRequestContactsImportContacts;
import org.telegram.tl.TLVector;

/**
 * Singleton Concurrency Pattern
 */

public class TLRepositoryProd extends TLAbsRepository implements MainContract.Repository {


  private static Logger l = Logger.getLogger("1");
  private ArrayList<UserContact> contactList = new ArrayList<>();
  private TelegramApiBridge bridge;

  private TelegramApi tlApi;
  private AuthAuthorization authorization;

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

  public void signIn(String confirmCode) throws IOException {
    //проверка кода
    smsCodeChecked = confirmCode;
    authorization = bridge.authSignIn(confirmCode);
    //получаем имя, фамилию юзера и записываем
    userFirstName = authorization.getUser().getFirstName();
    userLastName = authorization.getUser().getLastName();
    userFullName = userFirstName + " " + userLastName;
    userId = authorization.getUser().getId();

  }

  public void getMessages() {

  }

  public ArrayList<IContact> getContactList() throws IOException {
    if (contactList.isEmpty()  || true) {
      contactList = bridge.contactsGetContacts();
    }
    ArrayList<IContact> contactListJavaGram = new ArrayList<>();
    for (UserContact user : contactList){
      contactListJavaGram.add(new TgContact(user, Configs.IMG_DEFAULT_USER_PHOTO_41_41, null));
      try {
        sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return contactListJavaGram;
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
    catch (NullPointerException e) {
      e.printStackTrace();
      l.warning("Ошибка загрузки изображения!");
    }
    return img;
  }

  public String getSmsCodeChecked() {
    return smsCodeChecked;
  }

  @Override
  public Integer addContact(InputContact inputContact) throws IOException {
    boolean replace = false;
    TLVector<TLInputContact> inputContacts = new TLVector();
    inputContacts.add(inputContact.createTLInputContact());
    TLRequestContactsImportContacts tlRequestContactsImportContacts = new TLRequestContactsImportContacts(inputContacts, replace);
    TLImportedContacts tlImportedContacts = (TLImportedContacts)tlApi.doRpcCall(tlRequestContactsImportContacts);
    if (tlImportedContacts.getImported().size() == 0) {
      return null;
    } else {
      TLImportedContact tlImportedContact = (TLImportedContact)tlImportedContacts.getImported().get(0);
      return tlImportedContact.getClientId() == inputContact.getClientId() ? tlImportedContact.getUserId() : null;
    }
  }

  public void logOut() {
    try {
      bridge.authLogOut();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void signUp(String smsCode, String firstName, String lastName) throws IOException {
    AuthAuthorization auth = bridge.authSignUp(smsCode, firstName, lastName);
  }

  public TgContact searchUserWorlWide(String phone){
   return null;
  }
  //добавление юзера в контакт лист, дурацоке название
  @Override
  public void getCurrentUser() throws IOException {

    InputContact contact = new InputContact(0,"79659363762","Yota" , "79659363762");
    TLVector<TLInputContact> v = new TLVector();
    v.add(contact.createTLInputContact());
    TLRequestContactsImportContacts ci = new TLRequestContactsImportContacts(v, false);
    TLImportedContacts ic = this.tlApi.doRpcCall(ci);
    Log.info(ic.getUsers().size() + " ic.getUsers()");

    TLVector<TLImportedContact> listIC = ic.getImported();
    for (TLImportedContact c : listIC){
        Log.info("TLImportedContact" + c.getUserId());
    }
    System.out.println(listIC.isEmpty());
  }

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
