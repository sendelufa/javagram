package javagram;

import javagram.Presenter.PrChat;
import javagram.Presenter.PrEnterPhone;
import javagram.View.ViewChat;
import javagram.View.ViewEnterPhone;
import javagram.WindowGUI.WindowHandler;
import javax.swing.SwingUtilities;


/**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

public class Loader {

  public static void main(String[] args) {
    //Read config parameters and text strings
    Configs.read();

    //start app with first form with Phone enter

      WindowHandler.startFrame();
      /*ViewChat view = new ViewChat();
      view.setPresenter(new PrChat(new ViewChat()));*/
      ViewEnterPhone view = new ViewEnterPhone();
      view.setPresenter(new PrEnterPhone(view));






  }

}