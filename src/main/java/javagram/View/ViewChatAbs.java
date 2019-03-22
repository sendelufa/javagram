/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.View;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.Log;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public abstract class ViewChatAbs {
  //inner params
  protected JPanel mainPanel;
  protected JPanel panelHeadline;
  //Headline of Form
  protected JPanel pnlBtnExit;
  protected JPanel pnlBtnMinimize;
  protected JPanel pnlMicroLogo;
  protected JPanel pnlTitleBarUserPic;
  protected JPanel pnlTitleBarSettings;
  protected JPanel pnlChatsTitle;
  protected JTextPane поискTextPane;
  protected JPanel pnlContanctsId1;
  protected JPanel pnlUserPhoto1;
  protected JPanel pnlUserPhoto2;
  protected JPanel pnlChatList;
  protected JPanel pnlSearch;
  protected JPanel pnlContactsCol;
  protected JPanel pnlList;
  protected JPanel pnlNewChat;
  protected JPanel pnlMainCurrentChat;
  protected JPanel pnlCurrentChatUser;
  protected JPanel pnlCurrentChatUserPhoto;
  protected JPanel pnlCurrentChatUserEdit;
  protected JPanel pnlTextInLeft;
  protected JPanel pnlTextInCenter;
  protected JPanel pnlTextInRight;
  protected JPanel pnlMsgOutTop;
  protected JPanel pnlMsgOutBottom;
  protected JPanel pnlMsgTip;
  protected JTextPane txtEnterMessage;
  protected JPanel panelTopBar;
  protected JPanel pnlTestConsole;
  protected JButton LOGOUTButton;
  protected JPanel pnlContactsList;
  protected JButton setContactsJListButton;
  protected JScrollPane contactsJScroll;
  protected JButton lblBtnClearContacts;
  protected JLabel lblFullUserNameTopBar;
  protected JLabel lblTitleBarUserPic;
  protected JPanel pnlDialodMessages;
  protected JButton btnAddMsgOutgoing;
  protected JScrollPane messagesJScroll;

  //Resources - Images
  private BufferedImage microLogo, imgTitleBarUserPic, imgTitleBarSettings;
  protected BufferedImage imgChatsTitle, imgUserPhoto1, imgUserPhoto2, imgUserPhotoListSelected;
  private BufferedImage imgNewChat, imgCurrentChatUserEdit, imgTextInLeft, imgTextInCenter;
  private BufferedImage imgTextInRight, imgMsgOutTop, imgMsgOutBottom, imgMsgTip;

  public ViewChatAbs() {
    //set images
    try {
      microLogo = ImageIO.read(new File("res/img/logo-micro.png"));
      imgTitleBarUserPic = ImageIO.read(new File("res/img/mask-blue-mini.png"));
      imgTitleBarSettings = ImageIO.read(new File("res/img/icon-settings.png"));
      imgChatsTitle = ImageIO.read(new File("res/img/icon-search.png"));
      imgUserPhoto1 = ImageIO.read(new File("res/img/mask-white-online.png"));
      imgUserPhoto2 = ImageIO.read(new File("res/img/mask-gray-online.png"));
      imgUserPhotoListSelected = ImageIO.read(new File("res/img/mask-white-online-select.png"));
      imgNewChat = ImageIO.read(new File("res/img/icon-plus.png"));
      imgCurrentChatUserEdit = ImageIO.read(new File("res/img/icon-edit.png"));
      imgTextInLeft = ImageIO.read(new File("res/img/text_in_left.png"));
      imgTextInCenter = ImageIO.read(new File("res/img/text_in_center.png"));
      imgTextInRight = ImageIO.read(new File("res/img/button-send.png"));

    } catch (
        IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

  }

  //Custom UI components create
  private void createUIComponents() {

    pnlMicroLogo = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(microLogo, 5, 5, null);
      }
    };

    pnlTitleBarUserPic = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgTitleBarUserPic, 0, 10, null);
      }
    };

    pnlTitleBarSettings = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgTitleBarSettings, 0, 14, null);
      }
    };

    pnlChatsTitle = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgChatsTitle, 10, 10, null);
      }
    };

    pnlUserPhoto1 = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgUserPhoto1, 9, 14, null);
      }
    };

    pnlUserPhoto2 = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgUserPhoto2, 9, 14, null);
      }
    };

    pnlNewChat = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgNewChat, 20, 0, null);
      }
    };

    pnlCurrentChatUserPhoto = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgUserPhoto1, 0, 0, 35, 35, null);
      }
    };

    pnlCurrentChatUserEdit = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgCurrentChatUserEdit, 0, 0, null);
      }
    };

    pnlTextInLeft = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgTextInLeft, 27, 5, null);
      }
    };

    pnlTextInCenter = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgTextInCenter, 0, 5, 1920, 45, null);
      }
    };

    pnlTextInRight = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgTextInRight, 0, 5, null);
      }
    };

    pnlMsgOutTop = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgMsgOutTop, 0, 0, null);
      }
    };

    pnlMsgOutBottom = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgMsgOutBottom, 0, 0, null);
      }
    };

    pnlMsgTip = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(imgMsgTip, 0, 20, null);
      }
    };
  }


}