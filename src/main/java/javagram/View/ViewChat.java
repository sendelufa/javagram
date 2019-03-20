package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javagram.MainContract;
import javagram.Model.objects.TgContact;
import javagram.Model.objects.TgMessage;
import javagram.Presenter.PrChat;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.ItemContactList;
import javagram.View.formElements.MessagesDialog.IMessageItemDialog;
import javagram.View.formElements.MessagesDialog.MessageFactory;
import javagram.WindowGUI.WindowHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ViewChat extends ViewChatAbs implements MainContract.IViewChat {

  //Presenter
  private PrChat presenter;

  public ViewChat() {
    super();
    //PRESENTER
    setPresenter(new PrChat(this));

    initFrameComponents();

    setListeners();

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


}


