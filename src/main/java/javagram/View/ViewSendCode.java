package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

import java.awt.event.ComponentAdapter;
import javagram.Configs;
import javagram.MainContract;
import javagram.Presenter.PrSendCode;
import javagram.View.formElements.HeadLineForm;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ViewSendCode implements MainContract.IViewSendCode {

  private JPanel mainPanel;
  private JButton btnMinimize;
  private JButton btnExit;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JButton btnSendCode;
  private JPanel pnlBtnSend;
  //Headline of Form
  private JPanel pnlBtnExit;
  private JPanel pnlBtnMinimize;
  private JPanel pnlIcoPhone;
  private JTextPane txtCode;
  private JLabel lblBtnSend;
  private JLabel lblDescription;
  private JLabel lblPhoneNumber;
  private JLabel lblError;
  private JLabel lblBackToPhoneInput;
  //Resources - Images
  private BufferedImage bg;
  private BufferedImage logo;
  private BufferedImage imgBtn;
  private BufferedImage imgHeadClose;
  private BufferedImage imgHeadMin;
  private BufferedImage imgIcoPhone;
  //inner params

  //Presenter
  private PrSendCode presenter;

  public ViewSendCode() {
    //PRESENTER
    setPresenter(new PrSendCode(this));
    //set images
    try {
      bg = ImageIO.read(new File("res/img/background.jpg"));
      logo = ImageIO.read(new File("res/img/logo.png"));
      imgBtn = ImageIO.read(new File("res/img/button-background.png"));
      imgHeadClose = ImageIO.read(new File("res/img/icon-close.png"));
      imgHeadMin = ImageIO.read(new File("res/img/icon-hide.png"));
      imgIcoPhone = ImageIO.read(new File("res/img/icon-lock.png"));
    } catch (IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

    //Set Fonts
    lblDescription.setFont(WindowHandler.getMainFont(14));
    lblBtnSend.setFont(WindowHandler.getMainFont(30));
    lblPhoneNumber.setFont(WindowHandler.getMainFont(40));

    pnlBtnSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lblBackToPhoneInput.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //add base elements to head panel with max/min buttons
    HeadLineForm headLineForm = new HeadLineForm(HeadLineForm.DONT_SHOW_MINMAX);
    getMainPanel().add(headLineForm.getPanelHeadline(), 0);

    //check confirm code
    lblBtnSend.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        presenter.checkCode(txtCode.getText());

      }
    });

    //back to SignIn (input phone number)
    lblBackToPhoneInput.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        presenter.goBackToPhoneInput();

      }
    });

    lblBackToPhoneInput.addComponentListener(new ComponentAdapter() {
    });

    WindowHandler.setViewOnFrame(this);
  }

  @Override
  public void callViewChat() {
    new ViewChat();
  }

  @Override
  public void callViewEnterPhone(String phone) {
    new ViewEnterPhone(phone);
  }

  @Override
  public void callViewSignUp() {
    new ViewSignUp();
  }

  @Override
  public void showError(String strError) {
    clearError();
    lblError.setForeground(Color.RED);
    lblError.setText(strError);
  }

  @Override
  public void showErrorWrongCode() {
    showError(Configs.ERR_WRONG_CODE);
  }

  @Override
  public void showErrorUnknown() {
    showError("Неизвестная ошибка!");
  }

  @Override
  public void showInfo(String strError) {
    clearError();
    lblError.setText(strError);
  }

  @Override
  public void clearError() {
    lblError.setText("");
    lblError.setForeground(Color.WHITE);
  }

  @Override
  public void showLoadingProcess() {
    txtCode.setEnabled(false);
    lblBtnSend.setText("");
    lblBtnSend.setEnabled(false);
    ImageIcon imageIcon = new ImageIcon(Configs.IMG_LOADING_GIF_100);
    imageIcon.setImageObserver(lblBtnSend);
    lblBtnSend.setDisabledIcon(imageIcon);


  }

  @Override
  public void hideLoadingProcess() {
    lblBtnSend.setIcon(null);
    lblBtnSend.setText(Configs.BTN_CONTINUE);
    txtCode.setEnabled(true);
    lblBtnSend.setEnabled(true);

  }

  @Override
  public JPanel getMainPanel() {
    return mainPanel;
  }

  //Custom UI components create
  private void createUIComponents() {

    mainPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(bg, 0, 0, 800, 600, null);
      }
    };

    panelLogo = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, 0, 0, null);
      }
    };

    btnSendCode = new JButton() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 0, null);
      }
    };

    pnlBtnSend = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 3, null);
      }
    };

    pnlBtnExit = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadClose, 0, 0, null);
      }
    };

    pnlBtnMinimize = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadMin, 0, 0, null);
      }
    };

    pnlIcoPhone = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgIcoPhone, 0, 0, null);
      }
    };

    lblBackToPhoneInput = new JLabel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Configs.IMG_BUTTON_BACK_35_35, 0, 0, null);
      }
    };


  }

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrSendCode) presenter;
  }

  @Override
  public void setPhoneNumber(String phone) {
    String phoneFormat =
        "+" + phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4);
    lblPhoneNumber.setText(phoneFormat);
  }
}
