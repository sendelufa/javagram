package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.Presenter.PrChat;
import javagram.Presenter.objects.TgMessage;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.ItemContactList;
import javagram.View.formElements.MessagesDialog.IMessageItemDialog;
import javagram.View.formElements.MessagesDialog.MessageFactory;
import javagram.WindowGUI.GUIHelper;
import javagram.WindowGUI.WindowHandler;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class ViewChat extends ViewChatAbs implements MainContract.IViewChat {

  JList<IContact> list = new JList<>();
  //Presenter
  private PrChat presenter;

  public ViewChat() {
    super();
    //PRESENTER
    setPresenter(new PrChat(this));

    initFrameComponents();

    setListeners();

    GUIHelper.decorateScrollPane(contactsJScroll);
    GUIHelper.decorateScrollPane(messagesJScroll);

    WindowHandler.makeFrameResizable();
    WindowHandler.setViewOnFrame(this);
  }

  private void initFrameComponents() {
    //add base elements to head panel with max/min buttons
    HeadLineForm headLine = new HeadLineForm(HeadLineForm.SHOW_MINMAX);
    panelTopBar.add(headLine.getPanelHeadline(), BorderLayout.NORTH);
    //TODO сделать чтобы плавающая кнопка меняла положени при ресайзе и при открытии других панелей
    //set Float Buttons to Form
    setFloatPanels();
  }

  private void setListeners() {
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

    pnlFloatAddContactButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ViewContactAdd viewAddContact = new ViewContactAdd(WindowHandler.getFrameSize(),
              "Добавить новый контакт");
         WindowHandler
              .setModalFullScreenPanel(viewAddContact.getMainPanel(), viewAddContact.getBgPanel());
          //WindowHandler.frameSetContent(viewAddContact.getMainPanel());
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (FontFormatException e1) {
          e1.printStackTrace();
        }
//        try {
        // ViewContactAdd GlassPanel = new ViewContactAdd(WindowHandler.getFrameSize(),
        //     "Добавить пользователя");
        //  WindowHandler.setModalFullScreenPanel(GlassPanel.getForm(), GlassPanel.getBgPanel());
        //  WindowHandler.repaintFrame();
//        } catch (IOException e1) {
//          e1.printStackTrace();
//        } catch (FontFormatException e1) {
//          e1.printStackTrace();
//        }
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

    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        GUIHelper.decorateScrollBarActive(contactsJScroll.getVerticalScrollBar());
        Log.info("contactsJScroll mouseEntered");
        //contactsJScroll.revalidate();

      }
    });

    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        GUIHelper.decorateScrollBarInactive(contactsJScroll.getVerticalScrollBar());
        //contactsJScroll.revalidate();
        Log.info("contactsJScroll mouseExited");

      }
    });

  /*  pnlFloatAddContactButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Log.info("pnlFloatAddContactButton clicked");
        presenter.addContact();
      }
    });*/

    /**
     блок тестовых кнопок
     */

    LOGOUTButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.logOut();
      }
    });

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
  }

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrChat) presenter;
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
  public void showContactList(DefaultListModel<IContact> model) {
    list.setModel(model);
    list.setLayoutOrientation(JList.VERTICAL);

    contactsJScroll.setViewportView(list);
    //set design form to item in JList
    list.setCellRenderer(new DefaultListCellRenderer() {
      public Component getListCellRendererComponent(JList list,
          Object value, int index, boolean isSelected, boolean cellHasFocus) {
        IContact tc = (IContact) value;
        //select color and img mask for selected item
        Color color = isSelected ? new Color(213, 245, 255) : new Color(255, 255, 255);
        BufferedImage imgMask = isSelected ? imgUserPhotoListSelected : imgUserPhoto1;
        //add gui form ItemContactList to item in list
        ItemContactList cList = new ItemContactList(tc.getFullName(),
            String.valueOf(tc.getId()),
            tc.getTime() + " мин.", imgMask, tc.getSmallPhoto());
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
    if (userFirstName == null || userLastName == null || userFirstName.equals("")) {
      return "n/a error";
    }
    boolean hasLastName = (userLastName != null && !userLastName.equals(""));
    return hasLastName ? userFirstName.substring(0, 1) + userLastName.substring(0, 1)
        : userFirstName.substring(0, 1);
  }


  //set Float Buttons to Form
  private void setFloatPanels() {
    pnlFloatAddContactButton = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Shape circle = new Ellipse2D.Double(1, 1, 46, 46);
        g2d.setColor(new Color(0, 179, 230));
        g2d.fill(circle);
        g2d.draw(circle);

        Font exFont = WindowHandler.getMainFont(50);
        g2d.setFont(exFont);
        g2d.setColor(new Color(250, 250, 250));
        g2d.drawString("+", 10.0f, 41.0f);
      }
    };

    Dimension frameDimension = WindowHandler.getFrameSize();
    pnlFloatAddContactButton.setBounds(20, (int) frameDimension.getHeight() - 70, 50, 50);

    WindowHandler.setFloatComponents(pnlFloatAddContactButton);
    WindowHandler.showLayeredFloatButtons();
    WindowHandler.repaintFrame();
  }


}


