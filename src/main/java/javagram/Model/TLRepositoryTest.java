/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.Model;

import java.awt.Image;
import java.awt.TextArea;
import java.io.IOException;
import java.util.ArrayList;
import javagram.MainContract;
import org.javagram.response.object.UserContact;
import org.telegram.api.engine.RpcException;

public class TLRepositoryTest extends TLAbsRepository implements MainContract.Repository {
   private String confirmCode;

  private static volatile TLRepositoryTest instance;

  static TLRepositoryTest getInstance() {
    TLRepositoryTest localInstance = instance;

    if (localInstance == null){
      synchronized (TLRepositoryTest.class){
        localInstance = instance;
        if (localInstance == null){
          instance = localInstance = new TLRepositoryTest();
        }
      }
    }
    return localInstance;
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
  public void checkCode(String confirmCode) throws IOException {
      if (this.confirmCode.equals(confirmCode)){
        userFirstName = "Константин";
        userLastName = "Васильевич";
        userFullName = "Константин Васильевич";
        userId = 5555;
      }
      else
      {
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
  public ArrayList<UserContact> getContactList() throws IOException {
    return null;
  }

  @Override
  public void getMessages() {

  }

  @Override
  public void clearApiBridge() {

  }
}
