/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javagram.Presenter.objects.TgMessage;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import org.javagram.response.object.UserContact;

public interface MainContract {

  /**
   * Views
   **/
  interface IView {

    //main method init View
    void setPresenter(IPresenter presenter);

    //show various error
    void showError(String strError);

    //show various info
    void showInfo(String strError);

    void clearError();

    //show Loading Icon (animated)
    void showLoadingProcess();

    //hide Loading Icon (animated)
    void hideLoadingProcess();

    //main panel to show in frame
    JPanel getMainPanel();
  }

  interface IViewChat extends IView {

    void showContactList(DefaultListModel<IContact> tgContacts);

    void setUserFullNameLabelTop(String fullName);

    void setUserPhotoTop(Image userPhoto, String userFirstName, String userLastName);

    void showDialogMessages(DefaultListModel<TgMessage> tgMessages);

  }

  interface IViewEnterPhone extends IView {

/*    //set Phone, use if user go back from send code or registration screen
    void fillPhoneNumberTextField(String phone);*/

    //get View Send Code
    void callViewSendCode();

    //SHOW ERRORS AND INFO
    void showErrorPhoneEmpty();

    void showErrorPhoneFormat();

    void showInfoConnecting();
  }

  interface IViewSendCode extends IView {

    void setPhoneNumber(String phone);

    //main panel with dialogs, contact list, messages
    void callViewChat();

    void callViewEnterPhone();

    void callViewEnterPhone(String phone);

    //SHOW ERRORS AND INFO
    void showErrorWrongCode();

    void showErrorUnknown();
  }

  /**
   * Presenters
   **/
  interface IPresenter {

  }

  /**
   * Model
   **/
  interface Repository {

    boolean isPhoneRegistered();

    void checkPhoneRegistered(String phone);

    void checkCode(String confirmCode) throws IOException;

    void sendCode() throws IOException;

    String getUserPhone();

    void logOut();

    Image getUserPhoto();

    int getUserId();

    String getUserLastName();

    String getUserFirstName();

    String getUserFullName();

    ArrayList<IContact> getContactList() throws IOException;

    void getMessages();

    void clearApiBridge();
  }

  /**
   * Model Factory
   **/

  interface RepositoryFactory {

    Repository getModel();
  }

  /**
   * Objects
   **/

  interface IContact{
    String getFullName();
    String getFirstName();
    String getLastName();
    String getTime();
    int getId();
    String getLastMessage();
    BufferedImage getSmallPhoto();
    BufferedImage getBigPhoto();
    boolean isOnline();
  }
}