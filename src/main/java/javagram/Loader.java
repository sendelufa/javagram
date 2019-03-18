package javagram;

import javagram.View.ViewChat;
import javagram.View.ViewEnterPhone;
import javagram.WindowGUI.WindowHandler;



/**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

public class Loader {

  public static void main(String[] args) {
    //Read config parameters and text strings
    Configs.read();
    //start app with first form with Phone enter
      WindowHandler.startFrame();
      //new ViewChat();
      new ViewEnterPhone();

  }

}