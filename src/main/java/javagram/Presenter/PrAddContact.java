/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.Configs;
import javagram.MainContract;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import org.telegram.api.engine.RpcException;

public class PrAddContact implements MainContract.IPresenter {
  private MainContract.IViewAddContact view;
  private Repository repository = new TelegramProdFactory().getModel();

  public PrAddContact(MainContract.IViewAddContact view) {
    this.view = view;
  }

  public void addContact(String phone){
    //clean input phone String
    String phone_clean = phone.trim().replaceAll("[^0-9]", "");
    if (phone_clean.equals("")){
      view.showErrorPhoneEmpty();
      return;
    }
    if (phone_clean.length() != Configs.TL_REQUIRED_PHONE_LENGTH){
      view.showErrorPhoneFormat();
      return;
    }
    //repository.
    view.showError("запуск поиска контакта");
  }

 /* public void checkCode(String confirmCode) {
    view.showLoadingProcess();
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          //check confirm code
          repository.signIn(confirmCode);
        } catch (RpcException e) {
          if (e.getErrorTag().equals("PHONE_CODE_INVALID")) {
            view.showErrorWrongCode();
          } else if (e.getErrorTag().equals("PHONE_NUMBER_UNOCCUPIED")) {
            view.showInfo("PHONE_NUMBER_UNOCCUPIED go to SignUp");
            view.callViewSignUp();
          } else {
            view.showErrorUnknown();
          }
          return;
        } catch (IOException e) {
          e.printStackTrace();
          return;
        } finally {
          view.hideLoadingProcess();
        }

        //view chat if no exceptions
        view.callViewChat();
      }
    });
    thread.start();

  }*/
}
