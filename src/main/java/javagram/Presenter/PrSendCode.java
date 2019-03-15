/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import java.io.IOException;
import javagram.Configs;
import javagram.Model.TLHandler;
import javagram.View.ViewChat;
import javagram.View.IViewSendCode;
import javagram.View.ViewEnterPhone;
import javagram.WindowGUI.WindowHandler;
import org.telegram.api.engine.RpcException;

public class PrSendCode {

  private IViewSendCode view;

  public PrSendCode(IViewSendCode view) {
    this.view = view;
    WindowHandler.setViewOnFrame(this.view);
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
          TLHandler.getInstance().checkCode(confirmCode);
          view.showError("Логин успешен для пользователя: " + TLHandler.getInstance().getUserNameFull());
        } catch (RpcException e) {
          if (e.getErrorTag().equals("PHONE_CODE_INVALID")) {
            view.showError(Configs.ERR_WRONG_CODE);
          } else if (e.getErrorTag().equals("PHONE_NUMBER_UNOCCUPIED")) {
            view.showError("PHONE_NUMBER_UNOCCUPIED go to SignUp");

          } else {
            view.showError("Неизвестная ошибка!");
          }

          return;
        } catch (IOException e) {
          e.printStackTrace();
          return;
        } finally {
          view.hideLoadingProcess();
        }
        ViewChat chat = new ViewChat();
chat.setPresenter(new PrChat(chat));
      }
    });
    thread.start();

  }

  public void goBackToPhoneInput(){
    ViewEnterPhone viewEnterPhone = new ViewEnterPhone();
    viewEnterPhone.fillPhoneNumberTextField(TLHandler.getInstance().getUserPhone());
    viewEnterPhone.setPresenter(new PrEnterPhone(viewEnterPhone));
    TLHandler.getInstance().clearApiBridge();
  }


}
