package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javagram.Model.TgContact;
import javagram.Presenter.PrChat;
import javagram.ProfileAddContact;
import javagram.ProfileChangeContactData;
import javagram.ProfileChangeData;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.ItemContactList;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewChat implements IViewChat {

  //inner params
  final static boolean shouldFill = true;
  final static boolean shouldWeightX = true;
  final static boolean RIGHT_TO_LEFT = false;
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
  private JButton addMsgIncomingButton;
  private JPanel pnlContactsList;
  private JButton setContactsJListButton;
  private JScrollPane contactsJScroll;
  private JButton lblBtnClearContacts;
  //Resources - Images
  private BufferedImage microLogo;
  private BufferedImage imgHeadClose;
  private BufferedImage imgHeadMin;
  private BufferedImage imgTitleBarUserPic;
  private BufferedImage imgTitleBarSettings;
  private BufferedImage imgChatsTitle;
  private BufferedImage imgUserPhoto1, imgUserPhoto2, imgUserPhotoListSelected;
  private BufferedImage imgNewChat;
  private BufferedImage imgCurrentChatUserEdit;
  private BufferedImage imgTextInLeft;
  private BufferedImage imgTextInCenter;
  private BufferedImage imgTextInRight;
  private BufferedImage imgMsgOutTop;
  private BufferedImage imgMsgOutBottom;
  private BufferedImage imgMsgTip;

  //Presenter
  private PrChat presenter;

  DefaultListModel<TgContact> model = new DefaultListModel<>();


  public ViewChat() {
    try {
      imgHeadClose = ImageIO.read(new File("res/img/icon-close.png"));
      imgHeadMin = ImageIO.read(new File("res/img/icon-hide.png"));
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
      imgMsgOutTop = ImageIO.read(new File("res/img/message-out-top.png"));
      imgMsgOutBottom = ImageIO.read(new File("res/img/message-out-bottom.png"));
      imgMsgTip = ImageIO.read(new File("res/img/message-out-right.png"));
    } catch (IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

    //add base elements to head panel with max/min buttons
    HeadLineForm headLine = new HeadLineForm(HeadLineForm.SHOW_MINMAX);
    panelTopBar.add(headLine.getPanelHeadline(), BorderLayout.NORTH);


    //TODO сделать чтобы плавающая кнопка меняла положени при ресайзе и при открытии других панелей
    JPanel p = new JPanel(new CardLayout(100,100));
   p.setBorder(BorderFactory.createCompoundBorder(
       BorderFactory.createLineBorder(new Color(249,255,246 ), 3),
       BorderFactory.createEmptyBorder(25, 25, 25, 25)));
    p.setBackground(Color.GREEN);
    Dimension frameDimension = WindowHandler.getFrameSize();
    p.setBounds(20, (int)frameDimension.getHeight()-70, 26, 26);
    try {
      ProfileChangeData glassPanel = new ProfileChangeData(WindowHandler.getFrameSize(),
          "Настройки профиля");
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (FontFormatException e1) {
      e1.printStackTrace();
    }
    //WindowHandler.setModalFullScreenPanel(p, new JPanel());
    WindowHandler.setFloatComponents(p);
    WindowHandler.setLayeredFloatButtons();
    WindowHandler.repaintFrame();

    //temp
    pnlTitleBarSettings.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ProfileChangeData glassPanel = new ProfileChangeData(WindowHandler.getFrameSize(),
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
          ProfileAddContact GlassPanel = new ProfileAddContact(WindowHandler.getFrameSize(),
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
          ProfileChangeContactData GlassPanel = new ProfileChangeContactData(
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


        model = new DefaultListModel<>();
        model.ensureCapacity(50);
        for(int i = 0; i < 100; i++){
          model.addElement(new TgContact());
        }
        JList<TgContact> list = new JList<>(model);
        list.setLayoutOrientation(JList.VERTICAL);

        contactsJScroll.setViewportView(list);

        System.out.println("list size:" + model.getSize());
        list.setCellRenderer(new DefaultListCellRenderer() {
          public Component getListCellRendererComponent(JList list,
              Object value, int index, boolean isSelected, boolean cellHasFocus) {
            TgContact tc = (TgContact) value;
            //System.out.println(tc.getTime());
            Color color = isSelected ? new Color(213, 245, 255) : new Color(255, 255, 255);
            BufferedImage imgUser = isSelected ? imgUserPhotoListSelected : imgUserPhoto1;
            ItemContactList cList = new ItemContactList("имя:" + tc.getName(), tc.getLastMessage(), tc.getTime()+ " мин.", imgUser);
            cList.getMainPanel().setBackground(color);
            return cList.getMainPanel() ;
          }
        });

      }
    });
    lblBtnClearContacts.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        //contactsJScroll.remove(1);
        model.clear();
        WindowHandler.repaintFrame();
      }
    });
  }

  @Override
  public void showError(String strError) {
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

  //add component with repaint
  private void addComponentToPanel(JPanel p, Component c) {
    p.add(c);
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
  public void setPresenter(PrChat presenter) {
    this.presenter = presenter;
  }
}
