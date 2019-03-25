/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.View.formElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract.IContact;
import javagram.View.ViewUtils;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemContactList {

  private JPanel mainPanel;
  private JPanel pnlUserPhotoContainer;
  private JLabel lblName;
  private JLabel lblLastMessage;
  private JLabel lblTime;
  private JLabel lblUserPhoto;
  private JPanel pnlUserPhoto;

  private String fullName;
  private String lastMessage;
  private String time;
  private String initiates;
  private int contactId;

  private BufferedImage maskPhoto;
  private BufferedImage userPhoto;

  public ItemContactList(IContact contact, BufferedImage maskPhoto) {
    //set parameters
    this.fullName = contact.getFullName();
    this.lastMessage = contact.getLastMessage();
    this.time = contact.getTime() + " мин.";
    this.maskPhoto = maskPhoto;
    this.contactId = contact.getId();

    this.initiates = ViewUtils.getFullNameInitiates(contact.getFirstName(), contact.getLastName());

    if (contact.getSmallPhoto() == null) {
      //this.userPhoto = defaultPhoto;
      lblUserPhoto.setText(initiates);
      pnlUserPhoto.setBackground(getColor());
    } else {
      lblUserPhoto.setText("");
      this.userPhoto = contact.getSmallPhoto();
    }
    Log.info("name=" + this.fullName + " userPhoto null=  " + (userPhoto == null));

    //setUI
    lblName.setText(this.fullName);
    lblLastMessage.setText(this.lastMessage);
    lblTime.setText(this.time);


  }

  private Color getColor() {
    int colorIndex = 0;
    try {
      colorIndex = (contactId % Configs.COLORS_BG.length);
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
