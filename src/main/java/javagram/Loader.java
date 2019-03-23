package javagram;

import java.awt.Color;
import javagram.View.ViewAddContact;
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
    //IMessenger messengerModel = TLRepositoryProd.getInstance();
    //new ViewChat();
    new ViewEnterPhone();

    /*ViewAddContact vca = new ViewAddContact();
    vca.getMainPanel().setBackground(Color.black);
    vca.getMainPanel().setOpaque(true);
    WindowHandler.frameSetContent(vca.getMainPanel());  */  //new ViewSignUp();


  }

}