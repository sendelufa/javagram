/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.Presenter;

import javagram.Model.TelegramHandler;
import javagram.View.IViewChat;
import javagram.WindowGUI.WindowHandler;


public class PrChat {

  private IViewChat view;

  public PrChat(IViewChat view) {
    this.view = view;
    //set view to frame
    WindowHandler.setViewOnFrame(this.view);

    WindowHandler.repaintFrame();
    WindowHandler.makeFrameResizable();

    view.showError(String.valueOf(TelegramHandler.getUserId()));



  }


}
