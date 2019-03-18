/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import javagram.Model.TgContact;
import javagram.Model.TgMessage;
import javagram.Presenter.interfaces.IPresenter;
import javagram.View.interfaces.IViewChat;
import javax.swing.DefaultListModel;


public class PrChat implements IPresenter {

  DefaultListModel<TgContact> contactsListModel;
  DefaultListModel<TgMessage> messagesListModel;
  private IViewChat view;

  public PrChat(IViewChat view) {
    this.view = view;
    //set view to frame
//    this.view.setUserFullNameLabelTop(TLHandler.getInstance().getUserFullName());
  //  this.view.setUserPhotoTop(TLHandler.getInstance().getUserPhoto(),
      //  TLHandler.getInstance().getUserFirstName(), TLHandler.getInstance().getUserLastName());
  }

  public void getContactList() {
    contactsListModel = new DefaultListModel<>();
    contactsListModel.ensureCapacity(50);
    for (int i = 0; i < 100; i++) {
      contactsListModel.addElement(new TgContact());
    }
    view.showContactList(contactsListModel);
  }

  public void clearContactListModel() {
    contactsListModel.clear();
  }

  public void getDialogMessages() {
    messagesListModel = new DefaultListModel<>();
    messagesListModel.ensureCapacity(20);
    for (int i = 0; i < 8; i++) {
      messagesListModel.addElement(new TgMessage(1552927340-60*2*(8-i),i));
    }
    view.showDialogMessages(messagesListModel);
  }

}
