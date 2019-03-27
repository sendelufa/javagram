package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.CommonInterfaces.IFormattedText;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;
import javagram.Presenter.PrEditUserProfile;
import javagram.View.formElements.LayeredPaneBlackGlass;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;


public class ViewEditUserProfileModal extends LayeredPaneBlackGlass implements
    MainContract.IViewEditUserProfile, IFormattedText {

  //inner params
  File fileUserPhotoSelected;
  Image userPhotoNew;
  private JPanel mainPanel;
  private JPanel panelLogo;
  private JTextPane lbpDescPhone;
  private JPanel pnlBtnEdit;
  private JLabel lblBtnEdit;
  private JLabel lblDescription;
  private JLabel lblTitle;
  private JLabel lblError;
  private JLabel lblBackToChat;
  private JFormattedTextField txtPhone;
  private JTextField txtFirstName;
  private JTextField txtLastName;
  private JLabel lblUserPhoto;
  //Resources - Images
  private BufferedImage logo;
  private BufferedImage imgBtn;
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

    txtPhone.setToolTipText("Номер изменить нельзя");

    lblUserPhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    //Set Fonts
    lblBtnEdit.setFont(WindowHandler.getMainFont(30));
    lblTitle.setFont(WindowHandler.getMainFont(32));
    txtPhone.setFont(WindowHandler.getMainFont(30));
    txtFirstName.setFont(WindowHandler.getMainFont(30));
    txtLastName.setFont(WindowHandler.getMainFont(30));

    //change Layout to mainPanel fo Y axis position
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    presenter.getUserProfileData();

    // !!! ADD mainPanel to getContent() for right render
    getContent().add(getMainPanel(), BorderLayout.NORTH);
    WindowHandler
        .setModalFullScreenPanel(getContent(), getBgPanel());

    //LISTENERS

    lblUserPhoto.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        fileUserPhotoSelected = ViewUtils.getPhotoFileChooser();
        Log.warning(fileUserPhotoSelected.getAbsolutePath());
        if (fileUserPhotoSelected.exists()) {
          try {
            Image uploadedPhoto = ImageIO.read(fileUserPhotoSelected);
            Log.warning("ширина выбранного файла:" + ((BufferedImage) uploadedPhoto).getWidth());
            fillUserPhoto(uploadedPhoto);
            WindowHandler.repaintFrame();
          } catch (IOException e1) {
            e1.printStackTrace();
            showError("Ошибка чтения файла! Пожалуйста, попробуйте еще раз");
          }
        } else {
          fileUserPhotoSelected = null;
          showError("Выбран несуществующий файл! Пожалуйста, попробуйте еще раз");
        }

      }
    });

    WindowHandler.repaintFrame();

    //edit profile
    lblBtnEdit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        presenter.setNewUserData(userPhotoNew, txtFirstName.getText().trim(),
            txtLastName.getText().trim());
      }
    });

    //back to Chat
    lblBackToChat.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        closeModalView();
      }
    });
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
    try {
      txtFirstName.setText(data[0]);
      txtLastName.setText(data[1]);
      txtPhone.setText(data[2]);
    } catch (ArrayIndexOutOfBoundsException e) {
      Log.warning("Неверно передан массив String[] data. ArrayIndexOutOfBoundsException");
      showError("Ошибка ответа от сервера: ArrayIndexOutOfBoundsException");
    }
  }

  //fill preview of userPhoto
  @Override
  public void fillUserPhoto(Image photo) {
    if (photo == null) {
      ImageIcon imageIcon = new ImageIcon(Configs.IMG_USER_PHOTO_EMPTY_160);
      lblUserPhoto.setIcon(imageIcon);
    } else {
      userPhotoNew = resizeAndCropImage(photo, 160, 160);
      ImageIcon imageIcon = new ImageIcon(userPhotoNew);
      lblUserPhoto.setIcon(imageIcon);
    }
  }

  //CROP AND RESIZE

  private Image resizeAndCropImage(Image photo, int width, int height) {
    BufferedImage buffPhoto = cropImageInSquare((BufferedImage) photo);
    return buffPhoto
        .getScaledInstance(width, height, Image.SCALE_SMOOTH); //or use it however you want
  }

  private BufferedImage cropImageInSquare(BufferedImage photo) {
    return photo.getWidth() >= photo.getHeight() ? cropWidthLong(photo) : cropHeightLong(photo);
  }

  private BufferedImage cropHeightLong(BufferedImage photo) {
    int pointX = 0;
    int pointY = (photo.getHeight() - photo.getWidth()) / 2;
    Log.info("pointX=" + pointX + " pointY=" + pointY);
    return cropFromPoint(photo, pointX, pointY, photo.getWidth());
  }

  private BufferedImage cropWidthLong(BufferedImage photo) {
    int pointX = (photo.getWidth() - photo.getHeight()) / 2;
    int pointY = 0;
    Log.info("pointX=" + pointX + " pointY=" + pointY);
    return cropFromPoint(photo, pointX, pointY, photo.getHeight());
  }

  private BufferedImage cropFromPoint(BufferedImage photo, int pointX, int pointY, int sideLenght) {
    BufferedImage img = photo
        .getSubimage(pointX, pointY, sideLenght,
            sideLenght); //fill in the corners of the desired crop location here
    BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(),
        BufferedImage.TYPE_INT_RGB);
    Graphics g = copyOfImage.createGraphics();
    g.drawImage(img, 0, 0, null);
    return copyOfImage;
  }
  //CROP AND RESIZE

  @Override
  public void setPresenter(MainContract.IPresenter presenter) {
    this.presenter = (PrEditUserProfile) presenter;
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

    pnlBtnEdit = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgBtn, 0, 3, null);
      }
    };

    lblBackToChat = new JLabel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Configs.IMG_BUTTON_BACK_35_35, 0, 0, null);
      }
    };


  }
}
