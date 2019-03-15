package javagram.View.formElements; /**
 * Project Javagram Created by Shibkov Konstantin on 24.12.2018.
 * FORM to add head panel with minimize/exit and other buttons
 */

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;


public class HeadLineForm {

  //fields
  private JPanel basePanel;
  private JButton btnMinimize;
  private JButton btnExit;
  private JPanel panelHeadline;
  //Headline of Form
  private JPanel pnlBtnExit;
  private JPanel pnlBtnMinimize;
  private JPanel pnlBtnMaximize;
  private JPanel pnlBtnNormalize;
  //Resources - Images
  private BufferedImage imgHeadClose, imgHeadMin, imgHeadMaximize, imgHeadNormalize;

  //inner params

  public static final int DONT_SHOW_MINMAX = 0;
  public static final int SHOW_MINMAX = 1;


  public HeadLineForm(int showMinMax) {
    try {
      imgHeadClose = ImageIO.read(new File("res/img/icon-close.png"));
      imgHeadMin = ImageIO.read(new File("res/img/icon-hide.png"));
      imgHeadMaximize = ImageIO.read(new File("res/img/icon-maximize.png"));
      imgHeadNormalize = ImageIO.read(new File("res/img/icon-normalize.png"));
    } catch (IOException e) {
      System.err.println("Неудалось загрузить картинки!");
      e.printStackTrace();
    }

    //set visible to button maximize
    boolean visibleMinMax = showMinMax == 1;
    pnlBtnMaximize.setVisible(visibleMinMax);

    //add Adapter to Drag window over the screen
    MouseAdapterDrag();

    //Add Listener to Headline Buttons

    //Close app
    pnlBtnExit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        System.exit(1);
      }
    });

    //Minimize frame
    pnlBtnMinimize.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        WindowHandler.minimizeFrame();
      }
    });

    //Maximize frame
    pnlBtnMaximize.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        WindowHandler.maximizeFrame();
        pnlBtnMaximize.setVisible(false);
        pnlBtnNormalize.setVisible(true);
      }
    });

    //set frame to default state
    pnlBtnNormalize.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        WindowHandler.normalizeFrame();
        pnlBtnMaximize.setVisible(true);
        pnlBtnNormalize.setVisible(false);
      }
    });



  }

  //add to Headline Jpanel MouseAdapter to drag Windows over the screen
  public void MouseAdapterDrag() {
    Point point = new Point();
    // The mouse listener and mouse motion listener we add here is to simply
    // make our frame dragable.
    panelHeadline.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        point.x = e.getX();
        point.y = e.getY();
      }
    });
    panelHeadline.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        Point p = WindowHandler.getLocation();
        WindowHandler.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
      }
    });
  }

  void viewMaximizeButton(){
    pnlBtnMaximize.setVisible(true);
    pnlBtnNormalize.setVisible(false);
  }

  //Custom UI components create
  private void createUIComponents() {

    pnlBtnExit = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadClose, 0, 0, null);
      }
    };

    pnlBtnMinimize = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadMin, 0, 0, null);
      }
    };

    pnlBtnMaximize = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadMaximize, 0, 0, null);
      }
    };

    pnlBtnNormalize = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgHeadNormalize, 0, 0, null);
      }
    };



  }

  public JPanel getMainPanel() {

    return basePanel;
  }

  public JPanel getPanelHeadline() {

    return panelHeadline;
  }

}
