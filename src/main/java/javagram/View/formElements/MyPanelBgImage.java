/**
 * Project Javagram
 * Created by Shibkov Konstantin on 03.01.2019.
 */
package javagram.View.formElements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyPanelBgImage extends JPanel {
    private BufferedImage img;
    public MyPanelBgImage(String strImg){
        super();
        try {
            img = ImageIO.read(new File(strImg));
        } catch (IOException e) {
            System.err.println("Неудалось загрузить картинки для main.java.LayeredPaneBlackGlass!");
            e.printStackTrace();
        }
    }
    public void paint(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,null);
    }
}

