/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
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

  public static String IMG_BG, IMG_LOGO_SIGN, IMG_BTN_BG, IMG_ICON_PHONE;
  public static String ERR_NO_IMAGES, ERR_PHONE_FORMAT, INFO_CONNECT_TELEGRAM, ERR_WRONG_CODE,
      ERR_PHONE_EMPTY, ERR_IMG_LOAD, ERR_CONTACT_NOT_FOUND, ERR_EMPTY_FIRST_NAME, ERR_EMPTY_FIRST_LAST_NAMES, ERR_UNKNOWN;
  //images for all formElement
  public static Image IMG_LOADING_GIF_100;
  public static BufferedImage IMG_BUTTON_BACK_35_35, IMG_DEFAULT_USER_PHOTO_41_41, IMG_TEST_USER_PHOTO_41_41;
  public static BufferedImage IMG_MESSAGE_OUTGOING_OUT_TOP_WEST, IMG_MESSAGE_OUTGOING_OUT_TOP_EAST;
  public static BufferedImage IMG_MESSAGE_OUTGOING_TIP, IMG_MESSAGE_OUTGOING_OUT_BOTTOM_WEST;
  public static BufferedImage IMG_MESSAGE_INCOMING_OUT_TOP_WEST, IMG_MESSAGE_INCOMING_OUT_TOP_EAST;
  public static BufferedImage IMG_MESSAGE_INCOMING_OUT_BOTTOM_EAST, IMG_MESSAGE_INCOMING_TIP,
      IMG_USER_PHOTO_EMPTY_160, IMG_SEARCH_ICON_30, IMG_SEARCH_STOP_30, IMG_USER_MASK,
      IMG_BUTTON_SEND;

  public static Color[] COLORS_BG;
  public static Font MAIN_FONT;
  public static String BTN_CONTINUE;
  //TL Connect settings
  public static String TL_SERVER, TL_APP_HASH;
  //System
  public static String INTERFACE_PHONE_MASK, REPOSITORY;
  public static char INTERFACE_PHONE_MASK_PLACEHOLDER;
  public static int TL_APP_ID, TL_REQUIRED_PHONE_LENGTH, API_DELAY_REQUEST, USER_ID;
  //Pathes
  public static String PATH_APP_DATA, PATH_USER_PHOTO;
  //FONTS
  public static File FONT_FILE_REGULAR, FONT_FILE_BOLD;

  public static String LABEL_SIGNUP_START, LABEL_SIGNUP_DESC, TXT_FIND_CONTACT, TXT_WRITE_MESSAGE, TXT_EDIT_CONTACT, TXT_CHOOSE_IMAGE, TXT_VIEW_ENTER_PHONE, TXT_VIEW_SEND_CODE, LABEL_TITLE_FIRST_NAME, LABEL_TITLE_LAST_NAME;

  private static Properties props = new Properties();

  public static void read() {

    try {
      props.load(new InputStreamReader(new FileInputStream(new File("res/config.ini")),
          StandardCharsets.UTF_8));

      //TL Connect settings
      TL_SERVER = props.getProperty("TL_SERVER");
      TL_APP_HASH = props.getProperty("TL_APP_HASH");
      TL_APP_ID = Integer.parseInt(props.getProperty("TL_APP_ID"));
      TL_REQUIRED_PHONE_LENGTH = Integer.parseInt(props.getProperty("TL_REQUIRED_PHONE_LENGTH"));

      USER_ID = 0;

      REPOSITORY = props.getProperty("REPOSITORY");

      //System settings
      INTERFACE_PHONE_MASK = "+# (###) #######";
      INTERFACE_PHONE_MASK_PLACEHOLDER = '_';
      API_DELAY_REQUEST = 2000; //millis

      MAIN_FONT = Font.getFont(Font.SANS_SERIF);
      try {
        MAIN_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("res/font/OpenSansEmoji.ttf"))
            .deriveFont((float) 14);
      } catch (FontFormatException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

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
      IMG_DEFAULT_USER_PHOTO_41_41 = ImageIO.read(new File("res/img/user-photo-def.png"));
      IMG_TEST_USER_PHOTO_41_41 = ImageIO.read(new File("res/img/test_contact_list_photo.jpg"));

      IMG_MESSAGE_OUTGOING_OUT_TOP_WEST = ImageIO.read(new File("res/img/message-out-top.png"));
      IMG_MESSAGE_OUTGOING_OUT_TOP_EAST = ImageIO
          .read(new File("res/img/message-out-top-east.png"));
      IMG_MESSAGE_OUTGOING_OUT_BOTTOM_WEST = ImageIO
          .read(new File("res/img/message-out-bottom.png"));
      IMG_MESSAGE_OUTGOING_TIP = ImageIO.read(new File("res/img/message-out-right.png"));

      IMG_MESSAGE_INCOMING_OUT_TOP_WEST = ImageIO.read(new File("res/img/message-in-top-west.png"));
      IMG_MESSAGE_INCOMING_OUT_TOP_EAST = ImageIO
          .read(new File("res/img/message-in-top-east.png"));
      IMG_MESSAGE_INCOMING_OUT_BOTTOM_EAST = ImageIO
          .read(new File("res/img/message-in-bottom-east.png"));
      IMG_MESSAGE_INCOMING_TIP = ImageIO.read(new File("res/img/message-in-left.png"));
      IMG_USER_PHOTO_EMPTY_160 = ImageIO.read(new File("res/img/UserPhotoEmpty.png"));
      IMG_SEARCH_ICON_30 = ImageIO.read(new File("res/img/icon-search.png"));
      IMG_SEARCH_STOP_30 = ImageIO.read(new File("res/img/icon-search-stop.png"));
      IMG_USER_MASK = ImageIO.read(new File("res/img/mask-user.png"));
      IMG_BUTTON_SEND = ImageIO.read(new File("res/img/button-send.png"));

      //Сolors
      COLORS_BG = new Color[]{Color.decode("#fff176"), Color.decode("#f4ff81"),
          Color.decode("#84ffff"), Color.decode("#ff80ab"), Color.decode("#ff9e80"),
          Color.decode("#b9f6ca"), Color.decode("#ff8a80"), Color.decode("#bcaaa4")};

      //ERRORS WARNINGS INFO texts
      ERR_NO_IMAGES = props.getProperty("ERR_NO_IMAGES", "Неудалось загрузить картинки!");
      ERR_PHONE_FORMAT = props.getProperty("ERR_PHONE_FORMAT", "Неверный формат номера!");
      ERR_WRONG_CODE = props
          .getProperty("ERR_WRONG_CODE", "Неверный код подтверждения, попробуйте еще раз");
      ERR_PHONE_EMPTY = props.getProperty("ERR_PHONE_EMPTY", "Пожалуйста, введите номер телефона");
      ERR_IMG_LOAD = props.getProperty("ERR_IMG_LOAD", "Не удалось загрузить картинки!");
      ERR_CONTACT_NOT_FOUND = props.getProperty("ERR_CONTACT_NOT_FOUND", "Пользователь не найден!");

      ERR_EMPTY_FIRST_NAME= props.getProperty("ERR_EMPTY_FIRST_NAME", "Пожалуйста, введите Имя");
      ERR_EMPTY_FIRST_LAST_NAMES= props.getProperty("ERR_EMPTY_FIRST_LAST_NAMES", "Введите, пожалуйста, Имя и Фамилию");
      ERR_UNKNOWN= props.getProperty("ERR_UNKNOWN", "Извините, произошла неизвестная ошибка!");

      INFO_CONNECT_TELEGRAM = props
          .getProperty("INFO_CONNECT_TELEGRAM", "Подключение к серверам телеграм...");
      TXT_FIND_CONTACT = props
          .getProperty("TXT_FIND_CONTACT", "Найти контакт");
      TXT_WRITE_MESSAGE = props
          .getProperty("TXT_WRITE_MESSAGE", "Напишите сообщение...");
      TXT_EDIT_CONTACT = props
          .getProperty("TXT_EDIT_CONTACT", "Изменить пользователя");
      TXT_CHOOSE_IMAGE = props
          .getProperty("TXT_CHOOSE_IMAGE", "Выберите изображение");
      TXT_VIEW_ENTER_PHONE = props
          .getProperty("TXT_VIEW_ENTER_PHONE", "Введите код страны и номер вашего мобильного телефона");
      TXT_VIEW_ENTER_PHONE = props
          .getProperty("TXT_VIEW_ENTER_PHONE", "Введите код страны и номер вашего мобильного телефона");

      TXT_VIEW_SEND_CODE =  props
          .getProperty("TXT_VIEW_SEND_CODE", "<html><center>На данный номер телефона было отправлено<BR> SMS сообщение с кодом подтверждения.<BR> Пожалуйста, введите этот код ниже:</center><html>");
      //BUTTONS AND LABELS
      BTN_CONTINUE = props.getProperty("BTN_CONTINUE", "ПРОДОЛЖИТЬ");
      LABEL_TITLE_FIRST_NAME = props
          .getProperty("LABEL_TITLE_FIRST_NAME", "Имя");
      LABEL_TITLE_LAST_NAME = props
          .getProperty("LABEL_TITLE_LAST_NAME", "Фамилия");
      LABEL_SIGNUP_DESC = props
          .getProperty("LABEL_SIGNUP_DESC", "<html><center>Введите Имя и Фамилию для регистрации</center><html>");

      LABEL_SIGNUP_START = props
          .getProperty("LABEL_SIGNUP_START", "Начать общение");

      //PATHES
      PATH_APP_DATA = "res/AppData/";
      PATH_USER_PHOTO = PATH_APP_DATA + "UserPhoto/";

      //FONTS
      FONT_FILE_REGULAR = new File("res/font/OpenSansEmoji.ttf");
      FONT_FILE_BOLD = new File("res/font/Roboto-Bold.ttf");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}