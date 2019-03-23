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
import javagram.Presenter.PrEnterPhone;
import javagram.View.formElements.HeadLineForm;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ViewEnterPhone implements MainContract.IViewEnterPhone {

  //Presenter
  private PrEnterPhone presenter;

  //fields
  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JButton btnCheckPhone;
  private JPanel pnlBtnSend;
  //Headline of Form
  private JPanel pnlIcoPhone;
  private JFormattedTextField txtPhone;
  private JLabel lblBtnSend;
  private JLabel lblError;
  private JPanel panelButtonContainer;
  private JLabel lblLoading;
  private JPanel pnlLoading;
  private JLabel jLabel;
  //Resources - Images
  private BufferedImage bg;
  private BufferedImage logo;
  private BufferedImage imgBtn;
  private BufferedImage imgIcoPhone;
  //inner params

  public ViewEnterPhone(String phone){
    this();
    txtPhone.setText(phone);
  }

  public ViewEnterPhone() {
    //PRESENTER
    setPresenter(new PrEnterPhone(this, Configs.TL_REQUIRED_PHONE_LENGTH));
    //set images
    try {
      bg = ImageIO.read(new File(Configs.IMG_BG));
      logo = ImageIO.read(new File(Configs.IMG_LOGO_SIGN));
      imgBtn = ImageIO.read(new File(Configs.IMG_BTN_BG));
      imgIcoPhone = ImageIO.read(new File(Configs.IMG_ICON_PHONE));
    } catch (IOException e) {
      showError(Configs.ERR_NO_IMAGES);
      e.printStackTrace();
    }

    //add base elements to head panel with max/min buttons
    HeadLineForm headLineForm = new HeadLineForm(HeadLineForm.DONT_SHOW_MINMAX);
    getMainPanel().add(headLineForm.getPanelHeadline(), 0);

    txtPhone.setBorder(null);

    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //Listeners
    lblBtnSend.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        presenter.checkPhone(txtPhone.getText());
      }
    });

    IFormattedText.setTextFieldMask(txtPhone, Configs.INTERFACE_PHONE_MASK,
        Configs.INTERFACE_PHONE_MASK_PLACEHOLDER);
    //format phone text input field
    txtPhone.setText("9996624444");

    //view set to windowframe
    WindowHandler.setViewOnFrame(this);

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

    pnlBtnSend = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 3, null);
      }
    };

    pnlIcoPhone = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgIcoPhone, 0, 0, null);
      }
    };
  }

  @Override
  public JPanel getMainPanel() {

    return mainPanel;
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
  public void showInfoConnecting() {
    showInfo(Configs.INFO_CONNECT_TELEGRAM);

  }

  @Override
  public void showError(String strError) {
    clearError();
    lblError.setForeground(Color.RED);
    lblError.setText(strError);
  }

  @Override
  public void showInfo(String strError) {
    clearError();
    lblError.setText(strError);
  }

  @Override
  public void showLoadingProcess() {
    txtPhone.setEnabled(false);
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
    txtPhone.setEnabled(true);
    lblBtnSend.setEnabled(true);

  }

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrEnterPhone) presenter;
  }

//  @Override
//  public void fillPhoneNumberTextField(String phone) {
//    txtPhone.setText(phone);
//  }

  @Override
  public void clearError() {
    lblError.setText("");
    lblError.setForeground(Color.WHITE);
  }

  @Override
  public void callViewSendCode(){
    new ViewSendCode();
  }


}
