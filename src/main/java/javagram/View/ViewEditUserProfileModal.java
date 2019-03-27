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
import javagram.MainContract;
import javagram.Presenter.PrEditUserProfile;
import javagram.View.formElements.LayeredPaneBlackGlass;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ViewEditUserProfileModal extends LayeredPaneBlackGlass implements
    MainContract.IViewEditUserProfile, IFormattedText {

  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JPanel pnlBtnAddContact;
  private JLabel lblBtnEdit;
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
  private PrEditUserProfile presenter;

  public ViewEditUserProfileModal() {
    super(WindowHandler.getFrameSize());
    //PRESENTER
    setPresenter(new PrEditUserProfile(this));
    //set images
    try {
      logo = ImageIO.read(new File("res/img/logo-micro.png"));
      imgBtn = ImageIO.read(new File("res/img/button-background.png"));
    } catch (IOException e) {
      System.err.println("Не удалось загрузить картинки!");
      e.printStackTrace();
    }

    //format text filed, example "+7(999)9999999"
    IFormattedText.setTextFieldMask(txtPhone, Configs.INTERFACE_PHONE_MASK,
        ' ');

    txtPhone.setBorder(null);
    txtFirstName.setBorder(null);
    txtLastName.setBorder(null);

    txtPhone.setText("79659363762");
    txtPhone.setToolTipText("Номер изменить нельзя");

    //Set Fonts
    lblBtnEdit.setFont(WindowHandler.getMainFont(30));
    lblTitle.setFont(WindowHandler.getMainFont(32));
    txtPhone.setFont(WindowHandler.getMainFont(30));
    txtFirstName.setFont(WindowHandler.getMainFont(30));
    txtLastName.setFont(WindowHandler.getMainFont(30));



    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //check confirm code
    lblBtnEdit.addMouseListener(new MouseAdapter() {
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
        closeModalView();
      }
    });

    // !!! ADD mainPanel to getContent() for right render
    getContent().add(getMainPanel(), BorderLayout.NORTH);
    WindowHandler
        .setModalFullScreenPanel(getContent(), getBgPanel());

    presenter.getUserProfileData();
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
  public void showErrorEmptyFirstLast() {
    showError("Введите, пожалуйста, Имя и Фамилию");
  }

  @Override
  public void showErrorEmptyFirst() {
    showError("Пожалуйста, введите Имя");
  }

  @Override
  public void fillUserProfileData(String[] data) {

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
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrEditUserProfile) presenter;
  }
}
