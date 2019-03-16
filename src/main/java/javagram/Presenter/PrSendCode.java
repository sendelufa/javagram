/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.Model.TLHandler;
import javagram.Presenter.interfaces.IPresenter;
import javagram.View.interfaces.IViewSendCode;
import org.telegram.api.engine.RpcException;

public class PrSendCode implements IPresenter {
  private IViewSendCode view;

  public PrSendCode(IViewSendCode view) {
    this.view = view;
    view.setPhoneNumber(TLHandler.getInstance().getUserPhone());
    sendCode();
  }

  private void sendCode() {
    view.clearError();
    try {
      TLHandler.getInstance().sendCode();
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
          TLHandler.getInstance().checkCode(confirmCode);
        } catch (RpcException e) {
          if (e.getErrorTag().equals("PHONE_CODE_INVALID")) {
            view.showErrorWrongCode();
          } else if (e.getErrorTag().equals("PHONE_NUMBER_UNOCCUPIED")) {
            view.showInfo("PHONE_NUMBER_UNOCCUPIED go to SignUp");

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
    view.callViewEnterPhone(TLHandler.getInstance().getUserPhone());
  }


}
