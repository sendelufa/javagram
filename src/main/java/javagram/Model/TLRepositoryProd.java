/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.Model;

import static java.lang.Thread.sleep;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.telegram.api.auth.TLAuthorization;
import org.telegram.api.auth.TLExportedAuthorization;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.requests.TLRequestAuthExportAuthorization;
import org.telegram.api.requests.TLRequestAuthImportAuthorization;
import org.telegram.api.requests.TLRequestAuthSignIn;
import org.telegram.api.requests.TLRequestContactsImportContacts;
import org.telegram.api.requests.TLRequestUpdatesGetState;
import org.telegram.api.updates.TLState;
import org.telegram.tl.TLBytes;
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

      // experiment auth save
      InputStream inputStream = new FileInputStream(new File("res/auth.txt"));
      byte[] bytes = inputStream.readAllBytes();
      TLRequestAuthImportAuthorization authImport = new TLRequestAuthImportAuthorization(906836,
          new TLBytes(bytes));

      TLAuthorization exportedAuthorization = tlApi
          .doRpcCallNonAuth(authImport);

      exportedAuthorization.getUser().getId();

      // experiment auth save
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

    // experiment auth save
    TLExportedAuthorization test = tlApi
        .doRpcCall(new TLRequestAuthExportAuthorization(tlApi.getState().getPrimaryDc()));
    Log.info("User id TLExportedAuthorization:" + test.getId());
    OutputStream outputStream = new FileOutputStream(new File("res/auth.txt"));
    test.serializeBody(outputStream);

    Log.info("TLExportedAuthorization:" + outputStream.toString());

    outputStream.flush();
    outputStream.close();
    // experiment auth save

  }



  public void getMessages() {

  }

  @Override
  public ArrayList<IContact> getContactList(boolean forceReload) throws IOException {
    Log.info("Start getContactList");
    if (contactList.isEmpty() || forceReload) {
      Log.info("Start getContactList - get bridge.contactsGetContacts()");
      contactList = bridge.contactsGetContacts();
    }
    ArrayList<IContact> contactListJavaGram = new ArrayList<>();
    for (UserContact user : contactList) {
      contactListJavaGram.add(new TgContact(user));
    }
    Log.info("End getContactList");
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
    } catch (NullPointerException e) {
      e.printStackTrace();
      l.warning("Ошибка загрузки изображения!");
    }
    return img;
  }

  public String getSmsCodeChecked() {
    return smsCodeChecked;
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

  public TgContact searchUserWorlWide(String phone) {
    return null;
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
