/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.awt.Image;
import java.awt.image.BufferedImage;
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

  //init data on view
  public void getUserProfileData() {
    String[] names = {repository.getUserFirstName(), repository.getUserLastName(),
        repository.getUserPhone()};
    view.fillUserProfileData(names);
    view.fillUserPhoto(repository.getUserPhoto());
  }

  public void setNewUserData(Image newPhoto, String firstName, String lastName) {
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    if (isContactFieldsValid()) {
      view.showInfo("Обновление ваших данных на сервере...");
      if (repository.editUserProfile(newPhoto, firstName, lastName)) {
        view.showInfo("Обновление успешно завершено!");
      } else {
        view.showError("При обновлении произошла ошибка!");
      }
    }
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
