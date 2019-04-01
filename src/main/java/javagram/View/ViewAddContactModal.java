package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.CommonInterfaces.IFormattedText;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.MainContract.IPresenter;
import javagram.Presenter.PrAddContact;
import javagram.Presenter.PrChat;
import javagram.Presenter.PrSignUp;
import javagram.View.formElements.LayeredPaneBlackGlass;
import javagram.View.formElements.MyGlassPanel;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ViewAddContactModal extends LayeredPaneBlackGlass implements
    MainContract.IViewAddContact,
    IFormattedText {

  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JPanel pnlBtnAddContact;
  private JLabel lblBtnAddContact;
  private JLabel lblDescription;
  private JLabel lblTitle;
  private JLabel lblError;
  private JLabel buttonBackToPhoneInput;
  private JFormattedTextField txtPhone;
  private JTextField txtFirstName;
  private JTextField txtLastName;
  //Resources - Images
  private BufferedImage logo;
  private BufferedImage imgBtn;
  //inner params

  //Presenter
  private PrAddContact presenter;

  public ViewAddContactModal(PrChat presenterChat) {
    super(WindowHandler.getFrameSize());
    //PRESENTER
    setPresenter(new PrAddContact(this, presenterChat));
    //set images
    try {
      logo = ImageIO.read(new File("res/img/logo-micro.png"));
      imgBtn = ImageIO.read(new File("res/img/button-background.png"));
    } catch (IOException e) {
      System.err.println("Не удалось загрузить картинки!");
      e.printStackTrace();
    }

    txtPhone.setBorder(null);
    txtFirstName.setBorder(null);
    txtLastName.setBorder(null);

    //Set Fonts
    lblDescription.setFont(WindowHandler.getMainFont(14));
    lblBtnAddContact.setFont(WindowHandler.getMainFont(30));
    lblTitle.setFont(WindowHandler.getMainFont(32));
    txtPhone.setFont(WindowHandler.getMainFont(30));
    txtFirstName.setFont(WindowHandler.getMainFont(30));
    txtLastName.setFont(WindowHandler.getMainFont(30));

    //format text filed, example "+7(999)9999999"
    IFormattedText.setTextFieldMask(txtPhone, Configs.INTERFACE_PHONE_MASK,
        ' ');

    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //check confirm code
    lblBtnAddContact.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        presenter.addContact(txtPhone.getText().trim(), txtFirstName.getText().trim(),
            txtLastName.getText().trim());
      }
    });

    //back to SignIn (input phone number)
    buttonBackToPhoneInput.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        closeModalView();
      }
    });

   // !!! ADD mainPanel to getContent() for right render
    getContent().add(getMainPanel(), BorderLayout.NORTH);
    WindowHandler
        .setModalFullScreenPanel(getContent(), getBgPanel());
  }

  @Override
  public void showError(String strError) {
    clearError();
    lblError.setForeground(Color.RED);
    lblError.setText(strError);
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



  }

  @Override
  public void hideLoadingProcess() {


  }

  @Override
  public JPanel getMainPanel() {
    return mainPanel;
  }

  @Override
  public void closeModalView() {
    WindowHandler.removeModalFullScreenPanel();
  }


  @Override
  public void showErrorUserNotFound() {
    showError("Пользователь не найден!");
  }

  @Override
  public void showErrorPhoneEmpty() {
    showError(Configs.ERR_PHONE_EMPTY);

  }

  @Override
  public void showErrorPhoneFormat() {
    showError(Configs.ERR_PHONE_FORMAT);

  }

  @Override
  public void showErrorEmptyFirstLast() {
    showError("Введите, пожалуйста, Имя и Фамилию");
  }

  @Override
  public void showErrorEmptyFirst() {
    showError("Пожалуйста, введите Имя");
  }

  //Custom UI components create
  private void createUIComponents() {



    panelLogo = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logo, 0, 0, null);
      }
    };

    pnlBtnAddContact = new JPanel() {
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
  public void setPresenter(IPresenter presenter) {
    this.presenter = (PrAddContact) presenter;
  }
}
