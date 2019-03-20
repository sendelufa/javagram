/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram;

import java.awt.Image;
import javagram.Model.TgContact;
import javagram.Model.TgMessage;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;

public interface MainContract {

  /** Views **/
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

    void showContactList(DefaultListModel<TgContact> tgContacts);

    void setUserFullNameLabelTop(String fullName);

    void setUserPhotoTop(Image userPhoto, String userFirstName, String userLastName);

    void showDialogMessages(DefaultListModel<TgMessage> tgMesssages);

  }

  interface IViewEnterPhone extends IView {

    //set Phone, use if user go back from send code or registration screen
    void fillPhoneNumberTextField(String phone);

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

  /** Presenters **/
  interface IPresenter {
  }

  /** Model **/
  interface Repository {
    String loadMessage();
  }
}