package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EventListener;
import java.util.HashMap;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IContact;
import javagram.Presenter.PrChat;
import javagram.Presenter.objects.TgMessage;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.ItemContactList;
import javagram.View.formElements.LabelUserPhoto;
import javagram.View.formElements.MessagesDialog.IMessageItemDialog;
import javagram.View.formElements.MessagesDialog.MessageFactory;
import javagram.View.formElements.TextPrompt;
import javagram.WindowGUI.GUIHelper;
import javagram.WindowGUI.WindowHandler;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ViewChat extends ViewChatAbs implements MainContract.IViewChat {

  JList<IContact> list = new JList<>();
  DefaultListModel<IContact> model = new DefaultListModel<>();
  HashMap<String, EventListener> actionsListeners = new HashMap<>();
  //Presenter
  private PrChat presenter;

  public ViewChat() {
    super();
    //PRESENTER
    setPresenter(new PrChat(this));

    initFrameComponents();

    GUIHelper.decorateScrollPane(contactsJScroll);
    GUIHelper.decorateScrollPane(messagesJScroll);

    WindowHandler.makeFrameResizable();
    WindowHandler.setViewOnFrame(this);

    setListeners();

  }

  private void initFrameComponents() {
    //add base elements to head panel with max/min buttons
    HeadLineForm headLine = new HeadLineForm(HeadLineForm.SHOW_MINMAX);
    panelTopBar.add(headLine.getPanelHeadline(), BorderLayout.NORTH);
    //TODO сделать чтобы плавающая кнопка меняла положени при ресайзе и при открытии других панелей
    //set Float Buttons to Form
    setFloatPanels();

    TextPrompt tp7 = new TextPrompt("Найти контакт", txtSearch);
    tp7.setForeground(Color.GRAY);
    tp7.changeAlpha(0.5f);
    ImageIcon imageIcon = new ImageIcon(Configs.IMG_SEARCH_ICON_30);
    tp7.setIcon(imageIcon);

    imageIcon = new ImageIcon(Configs.IMG_SEARCH_STOP_30);

    lblClearSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    pnlFloatAddContactButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    pnlUserEditProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lblClearSearch.setIcon(imageIcon);


  }

  private void setListeners() {
    //init all actions, must be first
    setListenersActions();
    //temp
    pnlUserEditProfile.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        ViewEditUserProfileModal viewEditUserProfileModal = new ViewEditUserProfileModal(presenter);
      }
    });

    //CONTACTLIST

    list.setLayoutOrientation(JList.VERTICAL);

    contactsJScroll.setViewportView(list);
    //set design form to item in JList
    list.setCellRenderer(new DefaultListCellRenderer() {
      public Component getListCellRendererComponent(JList list,
          Object value, int index, boolean isSelected, boolean cellHasFocus) {
        IContact tc = (IContact) value;
        //select color and img mask for selected item
        Color color = isSelected ? new Color(213, 245, 255) : new Color(255, 255, 255);
        BufferedImage imgMask = isSelected ? imgUserPhotoListSelected : imgUserPhotoListNotSelected;
        //add gui form ItemContactList to item in list
        ItemContactList cList = new ItemContactList(tc, imgMask);
        cList.getMainPanel().setBackground(color);
        return cList.getMainPanel();
      }
    });

    //CONTACTLIST

    pnlFloatAddContactButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        ViewAddContactModal viewAddContact = new ViewAddContactModal();
      }
    });

    pnlCurrentChatUserEdit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        try {
          ViewContactChangeProfile GlassPanel = new ViewContactChangeProfile(
              WindowHandler.getFrameSize(), "Изменить пользователя");
          WindowHandler.setModalFullScreenPanel(GlassPanel.getContent(), GlassPanel.getBgPanel());
          WindowHandler.repaintFrame();
        } catch (IOException e1) {
          e1.printStackTrace();
        } catch (FontFormatException e1) {
          e1.printStackTrace();
        }
      }
    });

    //LISTENERS OF JLIST CONTACTLIST
    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        GUIHelper.decorateScrollBarActive(contactsJScroll.getVerticalScrollBar());
      }
    });

    list.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited(MouseEvent e) {
        GUIHelper.decorateScrollBarInactive(contactsJScroll.getVerticalScrollBar());
      }
    });

    list.addMouseListener((MouseAdapter) actionsListeners.get("selectItemContactList"));

    txtSearch.addKeyListener(new KeyAdapter() {
      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        showContactListFilter(txtSearch.getText().trim());

      }
    });
    /**
     блок тестовых кнопок
     */

    sendMessageButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.sendMessage();
      }
    });

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

      }
    });

    btnLoadPhotoContactList.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        presenter.refreshUserPhotos();
      }
    });


  }

  private void setListenersActions() {
    MouseAdapter selectItemContactList = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        txtEnterMessage.setText(list.getSelectedValue().getFullName());
        setFocusOnInputFieldMessage();
        lblDialogContactName.setText(list.getSelectedValue().getFullName());
        presenter.getDialogMessages(list.getSelectedValue().getId());
        //scroll to bottom
        JScrollBar vertical = messagesJScroll.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
      }
    };

    //set names to listeners
    actionsListeners.put("selectItemContactList", selectItemContactList);
  }

  private void setFocusOnInputFieldMessage() {
    txtEnterMessage.requestFocusInWindow();
    txtEnterMessage.setCaretPosition(txtEnterMessage.getText().length());
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
    this.model = model;
    list.setModel(this.model);
    WindowHandler.repaintFrame();
  }

  @Override
  public void repaintContactList() {
    list.repaint();
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

  //remove and set new Label with user photo
  @Override
  public void setUserPhotoTop(Image userPhoto, String userFirstName, String userLastName) {
    pnlTitleBarUserPic.removeAll();
    pnlTitleBarUserPic
        .add(new LabelUserPhoto(getFullNameInitiates(userFirstName, userLastName), userPhoto));
  }

  private String getFullNameInitiates(String userFirstName, String userLastName) {
    return ViewUtils.getFullNameInitiates(userFirstName, userLastName);
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
    pnlFloatAddContactButton.setOpaque(false);

    WindowHandler.setFloatComponents(pnlFloatAddContactButton);
    WindowHandler.showLayeredFloatButtons();
    WindowHandler.repaintFrame();
  }

  //Filter contact by String
  private synchronized void showContactListFilter(String searchString) {
    if (searchString.equals("")) {
      list.setModel(model);
    } else {
      DefaultListModel<IContact> searchModel = new DefaultListModel<>();
      list.setModel(searchModel);
      for (int i = 0; i < model.getSize(); i++) {
        if (model.get(i).getFullName().toLowerCase().contains(searchString.toLowerCase())) {
          searchModel.addElement(model.get(i));
        }
      }
    }

  }


}