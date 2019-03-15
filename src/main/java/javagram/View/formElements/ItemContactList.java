/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.View.formElements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemContactList {

  private JPanel mainPanel;
  private JPanel pnlUserPhoto1;
  private JLabel lblName;
  private JLabel lblLastMessage;
  private JLabel lblTime;

  private String name;
  private String lastMessage;
  private String time;

  private BufferedImage profilePhoto;

  public ItemContactList(String name, String lastMessage, String time, BufferedImage profilePhoto){
    this.name = name;
    this.lastMessage = lastMessage;
    this.time = time;
    this.profilePhoto = profilePhoto;

    lblName.setText(name);
    lblLastMessage.setText(lastMessage);
    lblTime.setText(time);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    pnlUserPhoto1 = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(profilePhoto, 9, 14, null);
      }
    };
  }
}
