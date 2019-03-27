/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.MainContract;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import org.javagram.core.StaticContainer;

public class PrEditUserProfile implements MainContract.IPresenter {

  private MainContract.IViewEditUserProfile view;
  private Repository repository = new TelegramProdFactory().getModel();

  private String phone, firstName, lastName;

  public PrEditUserProfile(MainContract.IViewEditUserProfile view) {
    this.view = view;
  }

  public String[] getUserProfileData() {
    return null;
  }

  private boolean isContactFieldsValid() {
    //check names
    if (firstName.equals("")) {
      if (lastName.equals("")) {
        view.showErrorEmptyFirstLast();
        return false;
      }
      view.showErrorEmptyFirst();
      return false;
    }
    return true;
  }
}
