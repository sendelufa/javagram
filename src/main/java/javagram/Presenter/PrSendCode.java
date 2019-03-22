/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.MainContract;
import javagram.MainContract.Repository;
import javagram.Model.TelegramProdFactory;
import org.telegram.api.engine.RpcException;

public class PrSendCode implements MainContract.IPresenter {
  private MainContract.IViewSendCode view;
  private Repository repository = new TelegramProdFactory().getModel();

  public PrSendCode(MainContract.IViewSendCode view) {
    this.view = view;
    view.setPhoneNumber(repository.getUserPhone());
    sendCode();
  }

  private void sendCode() {
    view.clearError();
    try {
      repository.sendCode();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void checkCode(String confirmCode) {
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

  }

  public void goBackToPhoneInput() {
    view.callViewEnterPhone(repository.getUserPhone());
  }


}
