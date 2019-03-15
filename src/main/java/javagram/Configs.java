/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.imageio.ImageIO;

public class Configs {

  public static String BG_IMAGE, LOGO_SIGN_IMAGE, BTN_BG_IMAGE, ICON_PHONE_IMAGE;
  public static String ERR_NO_IMAGES, ERR_PHONE_FORMAT, INFO_CONNECT_TELEGRAM, ERR_WRONG_CODE,
      ERR_PHONE_EMPTY;
  public static Properties props = new Properties();
  //images for all formElements
  public static BufferedImage LOADING_GIF_100, BUTTON_BACK_35_35;

  public static String BTN_CONTINUE;

  //TL Connect settings
  public static String TL_SERVER, TL_APP_HASH;
  public static int TL_APP_ID;

  //TODO set check for 11 for RELEASE!!!!
  public static final int SYS_PHONE_NUMBER_LENGTH = 10;

  public static void read() {

    try {
      props.load(new InputStreamReader(new FileInputStream(new File("res/config.ini")), StandardCharsets.UTF_8));

      //TL Connect settings
      TL_SERVER = props.getProperty("TL_SERVER");
      TL_APP_HASH = props.getProperty("TL_APP_HASH");
      TL_APP_ID = Integer.parseInt(props.getProperty("TL_APP_ID"));

      BG_IMAGE = props.getProperty("BG_IMAGE", "res/img/background.jpg");
      LOGO_SIGN_IMAGE = props.getProperty("LOGO_SIGN_IMAGE", "res/img/logo.png");
      BTN_BG_IMAGE = props.getProperty("BTN_BG_IMAGE", "res/img/button-background.png");
      ICON_PHONE_IMAGE = props.getProperty("ICON_PHONE_IMAGE", "res/img/icon-phone.png");

      //ERRORS WARNINGS INFO texts
      ERR_NO_IMAGES = props.getProperty("ERR_NO_IMAGES", "Неудалось загрузить картинки!");
      ERR_PHONE_FORMAT = props.getProperty("ERR_PHONE_FORMAT", "Неверный формат номера!");
      INFO_CONNECT_TELEGRAM = props.getProperty("INFO_CONNECT_TELEGRAM", "Подключение к серверам телеграм...");
      ERR_WRONG_CODE = props.getProperty("ERR_WRONG_CODE", "Неверный код подтверждения, попробуйте еще раз");
      ERR_PHONE_EMPTY = props.getProperty("ERR_PHONE_EMPTY", "Пожалуйста, введите номер телефона");

      //images file
      LOADING_GIF_100 = ImageIO.read(new File(props.getProperty("LOADING_GIF_100_IMAGE")));
      BUTTON_BACK_35_35 = ImageIO.read(new File(props.getProperty("BUTTON_BACK_35_35")));

      //BUTTONS AND LABELS
      BTN_CONTINUE = props.getProperty("BTN_CONTINUE", "ПРОДОЛЖИТЬ");

      //System settings

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}