/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.View.formElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javagram.Log;
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

  private BufferedImage maskPhoto;
  private BufferedImage userPhoto;

  public ItemContactList(String firstName, String lastName, String lastMessage, String time,
      BufferedImage maskPhoto, BufferedImage userPhoto, BufferedImage defaultPhoto) {
    //set parameters
    this.fullName = firstName + " " + lastName;
    this.lastMessage = lastMessage;
    this.time = time;
    this.maskPhoto = maskPhoto;

    this.initiates = ViewUtils.getFullNameInitiates(firstName, lastName);

    if (userPhoto == null) {
      //this.userPhoto = defaultPhoto;
      lblUserPhoto.setText(initiates);
      pnlUserPhoto.setBackground(new Color(getRandomColorChannel(), getRandomColorChannel(),
          getRandomColorChannel()));
    } else {
      lblUserPhoto.setText("");
      this.userPhoto = userPhoto;
    }
    Log.info("userPhoto null? " + (userPhoto == null));

    //setUI
    lblName.setText(this.fullName);
    lblLastMessage.setText(lastMessage);
    lblTime.setText(time);


  }

  private int getRandomColorChannel() {
    //TODO get Color from preseted list
    return (int) (Math.random() * 30) + 100;
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
