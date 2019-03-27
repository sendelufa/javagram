package javagram.View.formElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class panelEditUserPhoto extends JPanel {

  BufferedImage background;

  public panelEditUserPhoto(Image photo) {
    super();
    this.background = (BufferedImage) photo;
    setPreferredSize(new Dimension(160, 160));
    setOpaque(true);
    setBackground(Color.RED);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);

  }
}
