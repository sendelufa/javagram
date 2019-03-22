/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;

public class PrSignUp implements MainContract.IPresenterSignUp {

  private MainContract.IViewSignUp view;
  private Repository repository = new TelegramProdFactory().getModel();

  public PrSignUp(MainContract.IViewSignUp view) {
    this.view = view;
    view.setPhoneNumber(repository.getUserPhone());
  }

  @Override
  public void goBackToPhoneInput() {
    view.callViewEnterPhone(repository.getUserPhone());
  }

  @Override
  public void signUp(String firstName, String lastName) {
    Log.info(repository.getSmsCodeChecked());

    if(isValidFirstLastNames(firstName, lastName)){
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          view.showLoadingProcess();
          try {
            repository.signUp(repository.getSmsCodeChecked(), firstName, lastName);
            repository.signIn(repository.getSmsCodeChecked());
          } catch (IOException e) {
            e.printStackTrace();
            view.showError(e.getMessage());
            view.hideLoadingProcess();
          }
          //view chat if no exceptions
          view.hideLoadingProcess();
          view.callViewChat();
        }
      });
      thread.start();
    }

  }

  private boolean isValidFirstLastNames(String firstName, String lastName) {
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
