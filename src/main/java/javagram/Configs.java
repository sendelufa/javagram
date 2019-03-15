/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.imageio.ImageIO;

public class Configs {

  //TODO set check for 11 for RELEASE!!!!
  public static final int SYS_PHONE_NUMBER_LENGTH = 10;
  public static String IMG_BG, IMG_LOGO_SIGN, IMG_BTN_BG, IMG_ICON_PHONE;
  public static String ERR_NO_IMAGES, ERR_PHONE_FORMAT, INFO_CONNECT_TELEGRAM, ERR_WRONG_CODE,
      ERR_PHONE_EMPTY;
  //images for all formElement
  public static Image IMG_LOADING_GIF_100;
  public static BufferedImage IMG_BUTTON_BACK_35_35, IMG_DEFAULT_USER;
  public static BufferedImage IMG_MESSAGE_OUTGOING_OUT_TOP, IMG_MESSAGE_OUTGOING_OUT_BOTTOM, IMG_MESSAGE_OUTGOING_TIP;
  public static String BTN_CONTINUE;
  //TL Connect settings
  public static String TL_SERVER, TL_APP_HASH;
  //System
  public static String INTERFACE_PHONE_MASK;
  public static char INTERFACE_PHONE_MASK_PLACEHOLDER;
  public static int TL_APP_ID;
  private static Properties props = new Properties();

  public static void read() {

    try {
      props.load(new InputStreamReader(new FileInputStream(new File("res/config.ini")),
          StandardCharsets.UTF_8));

      //TL Connect settings
      TL_SERVER = props.getProperty("TL_SERVER");
      TL_APP_HASH = props.getProperty("TL_APP_HASH");
      TL_APP_ID = Integer.parseInt(props.getProperty("TL_APP_ID"));

      //System settings

      INTERFACE_PHONE_MASK = "+# (###) #######";
      INTERFACE_PHONE_MASK_PLACEHOLDER = '_';

      //images file
      //for gif constructor
      IMG_LOADING_GIF_100 = Toolkit.getDefaultToolkit()
          .createImage((props.getProperty("LOADING_GIF_100_IMAGE")));
      //for static images
      IMG_BUTTON_BACK_35_35 = ImageIO.read(new File(props.getProperty("BUTTON_BACK_35_35")));
      IMG_BG = props.getProperty("IMG_BG", "res/img/background.jpg");
      IMG_LOGO_SIGN = props.getProperty("IMG_LOGO_SIGN", "res/img/logo.png");
      IMG_BTN_BG = props.getProperty("IMG_BTN_BG", "res/img/button-background.png");
      IMG_ICON_PHONE = props.getProperty("IMG_ICON_PHONE", "res/img/icon-phone.png");
      IMG_DEFAULT_USER = ImageIO.read(new File("res/img/icon-default-user.png"));

      IMG_MESSAGE_OUTGOING_OUT_TOP = ImageIO.read(new File("res/img/message-out-top.png"));
      IMG_MESSAGE_OUTGOING_OUT_BOTTOM = ImageIO.read(new File("res/img/message-out-bottom.png"));
      IMG_MESSAGE_OUTGOING_TIP = ImageIO.read(new File("res/img/message-out-right.png"));

      //ERRORS WARNINGS INFO texts
      ERR_NO_IMAGES = props.getProperty("ERR_NO_IMAGES", "Неудалось загрузить картинки!");
      ERR_PHONE_FORMAT = props.getProperty("ERR_PHONE_FORMAT", "Неверный формат номера!");
      ERR_WRONG_CODE = props
          .getProperty("ERR_WRONG_CODE", "Неверный код подтверждения, попробуйте еще раз");
      ERR_PHONE_EMPTY = props.getProperty("ERR_PHONE_EMPTY", "Пожалуйста, введите номер телефона");

      INFO_CONNECT_TELEGRAM = props
          .getProperty("INFO_CONNECT_TELEGRAM", "Подключение к серверам телеграм...");

      //BUTTONS AND LABELS
      BTN_CONTINUE = props.getProperty("BTN_CONTINUE", "ПРОДОЛЖИТЬ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}