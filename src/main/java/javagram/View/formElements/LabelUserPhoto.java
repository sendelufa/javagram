package javagram.View.formElements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javagram.Configs;
import javagram.WindowGUI.WindowHandler;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelUserPhoto extends JLabel {

  private Image photo = null;
  private String initiates;

  public LabelUserPhoto(String initiates, Image photo) {
    super();
    this.initiates = initiates.trim();
    setFont(WindowHandler.getMainFontBold(20));
    setPreferredSize(new Dimension(50, 50));
    setHorizontalAlignment(JLabel.CENTER);
    setVerticalAlignment(JLabel.CENTER);
    setText(initiates);
    setVisible(true);
    setOpaque(true);

    //generate parameters for draw

    setText("");
    if (photo == null) {
      setText(this.initiates);
      this.photo = Configs.IMG_USER_MASK;
    } else {
      int photoWidth = getPreferredSize().width;
      int photoHeight = getPreferredSize().height;
      //compound photo and mask in 1 image
      this.photo = photo.getScaledInstance(photoWidth, photoHeight, Image.SCALE_SMOOTH);
      BufferedImage copyOfPhoto = new BufferedImage(photoWidth, photoHeight,
          BufferedImage.TYPE_INT_RGB);
      Graphics g = copyOfPhoto.createGraphics();
      g.drawImage(this.photo, 0, 0, null);
      g.drawImage(Configs.IMG_USER_MASK, 0, 0, null);
      this.photo = copyOfPhoto;
    }


  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(photo, 0, 0, null);

  }
}
