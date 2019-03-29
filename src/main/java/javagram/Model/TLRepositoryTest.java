/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.Model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.Model.objects.InputContact;
import javagram.Model.objects.TgContact;
import org.javagram.response.object.Message;
import org.javagram.response.object.UserContact;
import org.telegram.api.TLUserContact;
import org.telegram.api.TLUserProfilePhoto;
import org.telegram.api.engine.RpcException;

public class TLRepositoryTest extends TLAbsRepository implements MainContract.Repository {

  private static volatile TLRepositoryTest instance;
  private String confirmCode;

  static TLRepositoryTest getInstance() {
    TLRepositoryTest localInstance = instance;

    if (localInstance == null) {
      synchronized (TLRepositoryTest.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new TLRepositoryTest();
        }
      }
    }
    return localInstance;
  }

  @Override
  public ArrayList<Message> getMessagesHistoryByUserId(int userId) throws IOException {
    return null;
  }

  @Override
  public void sendMessage() throws IOException {

  }

  @Override
  public boolean isPhoneRegistered() {
    return isPhoneRegistered;
  }

  @Override
  public void checkPhoneRegistered(String phone) {
    userPhone = phone;
    isPhoneRegistered = true;
  }

  @Override
  public void sendCode() throws IOException {
    confirmCode = "11111";
  }

  @Override
  public void signIn(String confirmCode) throws IOException {
    if (this.confirmCode.equals(confirmCode)) {
      userFirstName = "Константин";
      userLastName = "Васильевич";
      userFullName = "Константин Васильевич";
      userId = 5555;
    } else {
      throw new RpcException(400, "PHONE_CODE_INVALID");
    }
  }

  @Override
  public String getUserPhone() {
    return userPhone;
  }

  @Override
  public void logOut() {

  }

  @Override
  public Image getUserPhoto() {
    return null;
  }

  @Override
  public int getUserId() {
    return userId;
  }

  @Override
  public String getUserLastName() {
    return userLastName;
  }

  @Override
  public String getUserFirstName() {
    return userFirstName;
  }

  @Override
  public String getUserFullName() {
    return userFullName;
  }

  @Override
  public ArrayList<IContact> getContactList() throws IOException {
    String[] firstNames = {"Саша", "Маша", "Иннокентий", "Судья Дредд", "Котик", "Явист", "Хирург",
        "Паша", "Алексей", "Василий"};
    String[] lastNames = {"Шар", "Круг", "Квадрат", "Треугольник", "Додекаэдр", "Шестигранник",
        "Многогранник", "Звезда", "Цилиндр", "Ромб"};
    String[] phones = {"+78881111111", "+78881111112", "+78881111113", "+78881111114",
        "+78881111115", "+78881111116",
        "+78881111117", "+78881111118", "+78881111119", "+78881111110"};

    ArrayList<IContact> userContacts = new ArrayList<>();
    int numberContacts = (int) (Math.random() * 50) + 150;
    System.out.println(numberContacts);
    for (int i = 0; i < numberContacts; i++) {

      //TLUserProfilePhoto userPhoto = new TLUserProfilePhoto(1, );
      int intFName = (int) (Math.random() * 10);
      int intLName = (int) (Math.random() * 10);


      TLUserContact u = new TLUserContact(i, firstNames[intFName],
          lastNames[intLName], 111, phones[intLName], null, null);
      UserContact c = new UserContact(u);
      userContacts.add(new TgContact("test", c));
    }
    return userContacts;
  }

  @Override
  public void signUp(String smsCode, String firstName, String lastName) {

  }

  @Override
  public int addContact(String phone, String firstname, String lastName) throws IOException {
    return 0;
  }

  @Override
  public void clearApiBridge() {

  }

  @Override
  public String getSmsCodeChecked() {
    return null;
  }

  @Override
  public ArrayList<IContact> getContactListForceReload() throws IOException {
    return null;
  }

  @Override
  public ArrayList<IContact> getContactList(boolean forceReload) throws IOException {
    return null;
  }

  @Override
  public BufferedImage getContactPhotoSmall(IContact contact) {
    return null;
  }

  @Override
  public boolean editUserProfile(BufferedImage newPhoto, String firstName, String lastName) {
    return false;
  }
}


