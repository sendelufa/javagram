/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.View;

    import java.awt.Image;
    import javagram.Model.TgContact;
    import javagram.Presenter.PrChat;
    import javax.swing.DefaultListModel;

//view for window with phone input
public interface IViewChat extends IView {

  void setPresenter(PrChat presenter);

  void showContactList(DefaultListModel<TgContact> tgContacts);

  void setUserFullNameLabelTop(String fullName);

  void setUserPhotoTop(Image userPhoto);

}
