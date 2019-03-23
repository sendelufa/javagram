package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.CommonInterfaces.IFormattedText;
import javagram.Configs;
import javagram.MainContract;
import javagram.Presenter.PrSignUp;
import javagram.View.formElements.HeadLineForm;
import javagram.View.formElements.LayeredPaneBlackGlass;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ViewAddContact extends LayeredPaneBlackGlass implements MainContract.IViewSignUp,
    IFormattedText {

  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JButton btnSignUp;
  private JPanel pnlSignUp;
  private JLabel lblBtnAddContact;
  private JLabel lblDescription;
  private JLabel lblTitle;
  private JLabel lblError;
  private JLabel buttonBackToPhoneInput;
  private JTextField txtFirstName;
  private JLabel lblFindedResult;
  private JFormattedTextField txtPhone;
  private JTextField txtLastName;
  //Resources - Images
  private BufferedImage bg;
  private BufferedImage logo;
  private BufferedImage imgBtn;
  //inner params

  //Presenter
  private PrSignUp presenter;

  public ViewAddContact() {
    super(WindowHandler.getFrameSize(), "Добавить контакт");
    //PRESENTER
    //setPresenter(new PrSignUp(this));
    //set images
    try {
      bg = ImageIO.read(new File("res/img/background.jpg"));
      logo = ImageIO.read(new File("res/img/logo-micro.png"));
      imgBtn = ImageIO.read(new File("res/img/button-background.png"));
    } catch (IOException e) {
      System.err.println("Не удалось загрузить картинки!");
      e.printStackTrace();
    }

    txtPhone.setBorder(null);

    //Set Fonts
    lblDescription.setFont(WindowHandler.getMainFont(14));
    lblBtnAddContact.setFont(WindowHandler.getMainFont(30));
    lblTitle.setFont(WindowHandler.getMainFont(32));
    txtPhone.setFont(WindowHandler.getMainFont(30));

    //format text filed, example "+7(999)9999999"
    IFormattedText.setTextFieldMask(txtPhone, Configs.INTERFACE_PHONE_MASK,
        Configs.INTERFACE_PHONE_MASK_PLACEHOLDER);

    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //add base elements to head panel with max/min buttons
    HeadLineForm headLineForm = new HeadLineForm(HeadLineForm.DONT_SHOW_MINMAX);
    getMainPanel().add(headLineForm.getPanelHeadline(), 0);

    //check confirm code
    lblBtnAddContact.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
      }
    });

    //back to SignIn (input phone number)
    buttonBackToPhoneInput.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        WindowHandler.removeModalFullScreenPanel();
      }
    });

    WindowHandler.setViewOnFrame(this);
  }

  @Override
  public void callViewChat() {

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
    lblBtnAddContact.setText("");
    lblBtnAddContact.setEnabled(false);
    ImageIcon imageIcon = new ImageIcon(Configs.IMG_LOADING_GIF_100);
    imageIcon.setImageObserver(lblBtnAddContact);
    lblBtnAddContact.setDisabledIcon(imageIcon);


  }

  @Override
  public void hideLoadingProcess() {
    lblBtnAddContact.setIcon(null);
    lblBtnAddContact.setText(Configs.BTN_CONTINUE);
    txtFirstName.setEnabled(true);
    txtLastName.setEnabled(true);
    lblBtnAddContact.setEnabled(true);

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
    lblTitle.setText(phoneFormat);
  }
}
