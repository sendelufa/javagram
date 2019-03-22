package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.Configs;
import javagram.MainContract;
import javagram.Presenter.PrSendCode;
import javagram.Presenter.PrSignUp;
import javagram.View.formElements.HeadLineForm;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ViewSignUp implements MainContract.IViewSignUp {

  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JButton btnSignUp;
  private JPanel pnlSignUp;
  private JLabel lblBtnSend;
  private JLabel lblDescription;
  private JLabel lblPhoneNumber;
  private JLabel lblError;
  private JLabel buttonBackToPhoneInput;
  private JTextField txtFirstName;
  private JTextField txtLastName;
  //Resources - Images
  private BufferedImage bg;
  private BufferedImage logo;
  private BufferedImage imgBtn;
  //inner params

  //Presenter
  private PrSignUp presenter;

  public ViewSignUp() {
    //PRESENTER
    setPresenter(new PrSignUp(this));
    //set images
    try {
      bg = ImageIO.read(new File("res/img/background.jpg"));
      logo = ImageIO.read(new File("res/img/logo-micro.png"));
      imgBtn = ImageIO.read(new File("res/img/button-background.png"));
    } catch (IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

    txtFirstName.setBorder(null);
    txtLastName.setBorder(null);

    //Set Fonts
    lblDescription.setFont(WindowHandler.getMainFont(14));
    lblBtnSend.setFont(WindowHandler.getMainFont(30));
    lblPhoneNumber.setFont(WindowHandler.getMainFont(40));

    txtLastName.setFont(WindowHandler.getMainFont(30));
    txtFirstName.setFont(WindowHandler.getMainFont(30));

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
      presenter.signUp(txtFirstName.getText().trim(), txtLastName.getText().trim());
      }
    });

    //back to SignIn (input phone number)
    buttonBackToPhoneInput.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        presenter.goBackToPhoneInput();

      }
    });

    buttonBackToPhoneInput.addComponentListener(new ComponentAdapter() {
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
  public void showError(String strError) {
    clearError();
    lblError.setForeground(Color.RED);
    lblError.setText(strError);
  }

  @Override
  public void showErrorEmptyFirstLast() {
    showError("Введите, пожалуйста, Имя и Фамилию");
  }

  @Override
  public void showErrorEmptyFirst() {
    showError("Пожалуйста, введите Имя");
  }

  @Override
  public void showErrorUnknown() {
    showError("Неизвестная ошибка!");
  }

  @Override
  public void showInfo(String strInfo) {
    clearError();
    lblError.setText(strInfo);
  }

  @Override
  public void clearError() {
    lblError.setText("");
    lblError.setForeground(Color.WHITE);
  }

  @Override
  public void showLoadingProcess() {
    txtFirstName.setEnabled(false);
    txtLastName.setEnabled(false);
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
    txtFirstName.setEnabled(true);
    txtLastName.setEnabled(true);
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

    btnSignUp = new JButton() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 0, null);
      }
    };

    pnlSignUp = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 3, null);
      }
    };



    buttonBackToPhoneInput = new JLabel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Configs.IMG_BUTTON_BACK_35_35, 0, 0, null);
      }
    };


  }

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrSignUp) presenter;
  }

  @Override
  public void setPhoneNumber(String phone) {
    String phoneFormat =
        "+" + phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4);
    lblPhoneNumber.setText(phoneFormat);
  }
}
