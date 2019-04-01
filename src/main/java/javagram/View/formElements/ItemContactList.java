/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.View.formElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javagram.CommonInterfaces.IHumanableDate;
import javagram.Configs;
import javagram.MainContract.IContact;
import javagram.WindowGUI.WindowHandler;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemContactList {

  private JPanel mainPanel;
  private JLabel lblName;
  private JLabel lblLastMessage;
  private JLabel lblTime;
  private JLabel lblUserPhoto;
  private JPanel pnlUserPhoto;

  private String initiates;

  private IContact contact;

  private BufferedImage maskPhoto;
  private Image userPhoto;

  public ItemContactList(IContact contact, BufferedImage maskPhoto) {
    //set parameters
    this.contact = contact;
    this.maskPhoto = maskPhoto;
    this.initiates = this.contact.getInitiates();

    //setPhoto or bg circle
    if (this.contact.getSmallPhoto() == null) {
      //this.userPhoto = defaultPhoto;
      lblUserPhoto.setText(this.initiates);
      pnlUserPhoto.setBackground(getColor());
    } else {
      lblUserPhoto.setText("");
      this.userPhoto = contact.getSmallPhoto();
    }

    lblName.setFont(WindowHandler.getMainFontBold(13));
    lblUserPhoto.setFont(WindowHandler.getMainFontBold(18));

    //setUI
    lblName.setText(this.contact.getLastMessage().isUnread() + " " + this.contact.getFullName());
    lblName.setToolTipText(this.contact.getFullName());
    if (this.contact.getLastMessage() != null) {

      lblLastMessage.setText(this.contact.getLastMessage().getMessageText());

      if (this.contact.getLastMessage().getFromId() == Configs.USER_ID) {
        lblLastMessage.setText("Вы: " + lblLastMessage.getText());
      }

    } else {
      lblLastMessage.setText("нет переписки");
    }
    try {
      String time = IHumanableDate.convertDate(contact.getLastMessage().getDate());
      lblTime.setText("<html>" + time.replaceAll(" ", "<br>") + "</html>");
    } catch (NullPointerException e) {
      lblTime.setText("");
    }
  }

  private Color getColor() {
    int colorIndex;
    try {
      colorIndex = (contact.getId() % Configs.COLORS_BG.length);
    } catch (ArithmeticException e) {
      e.printStackTrace();
      return new Color(181, 240, 240);
    }
    return Configs.COLORS_BG[colorIndex];
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    pnlUserPhoto = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        if (userPhoto != null) {
          userPhoto = userPhoto
              .getScaledInstance(pnlUserPhoto.getPreferredSize().width,
                  pnlUserPhoto.getPreferredSize().width,
                  Image.SCALE_SMOOTH);
        }
        g.drawImage(userPhoto, 0, 0, null);
      }
    };
    lblUserPhoto = new JLabel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(maskPhoto, 0, 0, null);
      }
    };
  }
}
