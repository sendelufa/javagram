/**
 * Project Javagram Created by Shibkov Konstantin on 13.03.2019.
 */
package javagram.View.formElements;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemContactList {

  private JPanel mainPanel;
  private JPanel pnlUserPhoto;
  private JLabel lblName;
  private JLabel lblLastMessage;
  private JLabel lblTime;
  private JLabel lblUserPhoto;

  private String name;
  private String lastMessage;
  private String time;

  private BufferedImage maskPhoto;
  private BufferedImage userPhoto;

  public ItemContactList(String name, String lastMessage, String time, BufferedImage maskPhoto, BufferedImage userPhoto){
    this.name = name;
    this.lastMessage = lastMessage;
    this.time = time;
    this.maskPhoto = maskPhoto;
    this.userPhoto = userPhoto;

    lblName.setText(name);
    lblLastMessage.setText(lastMessage);
    lblTime.setText(time);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    lblUserPhoto = new JLabel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(userPhoto, 0, 0, null);
      }
    };
  }
}
