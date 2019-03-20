package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javagram.MainContract;
import javagram.Model.TgContact;
import javagram.Model.TgMessage;
import javagram.Presenter.PrChat;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.ItemContactList;
import javagram.View.formElements.MessagesDialog.IMessageItemDialog;
import javagram.View.formElements.MessagesDialog.MessageFactory;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewChat implements MainContract.IViewChat {

  //inner params
  private JPanel mainPanel;
  private JPanel panelHeadline;
  //Headline of Form
  private JPanel pnlBtnExit;
  private JPanel pnlBtnMinimize;
  private JPanel pnlMicroLogo;
  private JPanel pnlTitleBarUserPic;
  private JPanel pnlTitleBarSettings;
  private JPanel pnlChatsTitle;
  private JTextPane поискTextPane;
  private JPanel pnlContanctsId1;
  private JPanel pnlUserPhoto1;
  private JPanel pnlUserPhoto2;
  private JPanel pnlChatList;
  private JPanel pnlSearch;
  private JPanel pnlContactsCol;
  private JPanel pnlList;
  private JPanel pnlNewChat;
  private JPanel pnlMainCurrentChat;
  private JPanel pnlCurrentChatUser;
  private JPanel pnlCurrentChatUserPhoto;
  private JPanel pnlCurrentChatUserEdit;
  private JPanel pnlTextInLeft;
  private JPanel pnlTextInCenter;
  private JPanel pnlTextInRight;
  private JPanel pnlMsgOutTop;
  private JPanel pnlMsgOutBottom;
  private JPanel pnlMsgTip;
  private JTextPane txtEnterMessage;
  private JPanel panelTopBar;
  private JPanel pnlTestConsole;
  private JButton LOGOUTButton;
  private JPanel pnlContactsList;
  private JButton setContactsJListButton;
  private JScrollPane contactsJScroll;
  private JButton lblBtnClearContacts;
  private JLabel lblFullUserNameTopBar;
  private JLabel lblTitleBarUserPic;
  private JPanel pnlDialodMessages;
  private JButton btnAddMsgOutgoing;
  private JScrollPane messagesJScroll;
  //Resources - Images
  private BufferedImage microLogo, imgTitleBarUserPic, imgTitleBarSettings;
  private BufferedImage imgChatsTitle, imgUserPhoto1, imgUserPhoto2, imgUserPhotoListSelected;
  private BufferedImage imgNewChat, imgCurrentChatUserEdit, imgTextInLeft, imgTextInCenter;
  private BufferedImage imgTextInRight, imgMsgOutTop, imgMsgOutBottom, imgMsgTip;

  //Presenter
  private PrChat presenter;

  public ViewChat() {
    //PRESENTER
    setPresenter(new PrChat(this));
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

    } catch (IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

    //

    //add base elements to head panel with max/min buttons
    HeadLineForm headLine = new HeadLineForm(HeadLineForm.SHOW_MINMAX);
    panelTopBar.add(headLine.getPanelHeadline(), BorderLayout.NORTH);

    //TODO сделать чтобы плавающая кнопка меняла положени при ресайзе и при открытии других панелей

    //set Float Buttons to Form
    setFloatPanels();

    //temp
    pnlTitleBarSettings.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ViewUserChangeProfile glassPanel = new ViewUserChangeProfile(WindowHandler.getFrameSize(),
              "Настройки профиля");
          WindowHandler.setModalFullScreenPanel(glassPanel.getForm(), glassPanel.getBgPanel());
          WindowHandler.repaintFrame();
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (FontFormatException e1) {
          e1.printStackTrace();
        }
      }
    });

    pnlNewChat.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ViewContactAdd GlassPanel = new ViewContactAdd(WindowHandler.getFrameSize(),
              "Добавить пользователя");
          WindowHandler.setModalFullScreenPanel(GlassPanel.getForm(), GlassPanel.getBgPanel());
          WindowHandler.repaintFrame();
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (FontFormatException e1) {
          e1.printStackTrace();
        }
      }
    });

    pnlCurrentChatUserEdit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ViewContactChangeProfile GlassPanel = new ViewContactChangeProfile(
              WindowHandler.getFrameSize(), "Изменить пользователя");
          WindowHandler.setModalFullScreenPanel(GlassPanel.getForm(), GlassPanel.getBgPanel());
          WindowHandler.repaintFrame();
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (FontFormatException e1) {
          e1.printStackTrace();
        }
      }
    });

    /**
     блок тестовых кнопок
     */

    setContactsJListButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.getContactList();
      }
    });

    lblBtnClearContacts.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //contactsJScroll.remove(1);
        presenter.clearContactListModel();
        WindowHandler.repaintFrame();
      }
    });
    btnAddMsgOutgoing.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.getDialogMessages();
      }
    });

    WindowHandler.makeFrameResizable();
    WindowHandler.setViewOnFrame(this);

    LOGOUTButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.logOut();
      }
    });
  }

  @Override
  public void showError(String strError) {
    txtEnterMessage.setText(strError);
  }

  @Override
  public void showInfo(String strError) {
    txtEnterMessage.setText(strError);
  }

  @Override
  public void clearError() {

  }

  @Override
  public void showLoadingProcess() {

  }

  @Override
  public void hideLoadingProcess() {

  }

  @Override
  public JPanel getMainPanel() {
    return mainPanel;
  }

  @Override
  public void showContactList(DefaultListModel<TgContact> model) {
    JList<TgContact> list = new JList<>(model);
    list.setLayoutOrientation(JList.VERTICAL);

    contactsJScroll.setViewportView(list);
    //set design form to item in JList
    list.setCellRenderer(new DefaultListCellRenderer() {
      public Component getListCellRendererComponent(JList list,
          Object value, int index, boolean isSelected, boolean cellHasFocus) {
        TgContact tc = (TgContact) value;
        //select color and img mask for selected item
        Color color = isSelected ? new Color(213, 245, 255) : new Color(255, 255, 255);
        BufferedImage imgMask = isSelected ? imgUserPhotoListSelected : imgUserPhoto1;
        //add gui form ItemContactList to item in list
        ItemContactList cList = new ItemContactList(tc.getName(),
            tc.getLastMessage(),
            tc.getTime() + " мин.", imgMask, tc.getPhoto());
        cList.getMainPanel().setBackground(color);
        return cList.getMainPanel();
      }
    });
  }

  @Override
  public void showDialogMessages(DefaultListModel<TgMessage> model) {
    JList<TgMessage> list = new JList<>(model);
    list.setLayoutOrientation(JList.VERTICAL);
    list.setDragEnabled(true);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    messagesJScroll.setViewportView(list);
    //set design form to item in JList
    list.setCellRenderer(new DefaultListCellRenderer() {
      public Component getListCellRendererComponent(JList list,
          Object value, int index, boolean isSelected, boolean cellHasFocus) {
        TgMessage m = (TgMessage) value;
        //add gui form TgMessage to item in list
        IMessageItemDialog item = MessageFactory
            .render(m.isOut() ? 2 : 1, m.getMessage(), m.getDate());
        return item.getMainPanel();
      }
    });

    WindowHandler.pack();
  }

  @Override
  public void setUserFullNameLabelTop(String fullName) {
    lblFullUserNameTopBar.setText(fullName);
  }

  @Override
  public void setUserPhotoTop(Image userPhoto, String userFirstName, String userLastName) {
    if (userPhoto == null) {
      lblTitleBarUserPic.setText(getFullNameInitiates(userFirstName, userLastName));
      return;
    }
    userPhoto = userPhoto
        .getScaledInstance(lblTitleBarUserPic.getPreferredSize().width,
            lblTitleBarUserPic.getPreferredSize().width,
            Image.SCALE_SMOOTH);
    ImageIcon icon = new ImageIcon(userPhoto);

    lblTitleBarUserPic.setIcon(icon);
  }

  private String getFullNameInitiates(String userFirstName, String userLastName) {
    boolean hasLastName = (userLastName != null && userLastName != "");
    return hasLastName ? userFirstName.substring(0, 1) + userLastName.substring(0, 1)
        : userFirstName.substring(0, 1);
  }


  //set Float Buttons to Form
  private void setFloatPanels() {
    JPanel p = new JPanel(new CardLayout(100, 100));
    p.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(249, 255, 246), 3),
        BorderFactory.createEmptyBorder(25, 25, 25, 25)));
    p.setBackground(Color.GREEN);
    Dimension frameDimension = WindowHandler.getFrameSize();
    p.setBounds(20, (int) frameDimension.getHeight() - 70, 26, 26);

    WindowHandler.setFloatComponents(p);
    WindowHandler.showLayeredFloatButtons();
    WindowHandler.repaintFrame();
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

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrChat) presenter;
  }
}


