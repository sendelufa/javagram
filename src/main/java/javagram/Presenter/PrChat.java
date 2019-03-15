/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import javagram.Model.TLHandler;
import javagram.Model.TgContact;
import javagram.View.IViewChat;
import javagram.WindowGUI.WindowHandler;
import javax.swing.DefaultListModel;


public class PrChat {

  DefaultListModel<TgContact> model = new DefaultListModel<>();
  private IViewChat view;

  public PrChat(IViewChat view) {
    this.view = view;
    //set view to frame
    WindowHandler.makeFrameResizable();
    WindowHandler.setViewOnFrame(this.view);

    this.view.setUserFullNameLabelTop(TLHandler.getInstance().getUserFullName());
    this.view.showError(String.valueOf(TLHandler.getInstance().getUserId()));
    this.view.setUserPhotoTop(TLHandler.getInstance().getUserPhoto(),
        TLHandler.getInstance().getUserFirstName(), TLHandler.getInstance().getUserLastName());
  }

  public void getContactList() {
    model = new DefaultListModel<>();
    model.ensureCapacity(50);
    for (int i = 0; i < 100; i++) {
      model.addElement(new TgContact());
    }
    view.showContactList(model);
  }

  public void clearModel() {
    model.clear();
  }


}
