/**
 * Project Javagram Created by Shibkov Konstantin on 04.03.2019.
 */
package javagram.WindowGUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javagram.Configs;
import javagram.Log;
import javagram.MainContract;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//Class for handle frame of app

public class WindowHandler {

  final static int FRAME_WIDTH = 1024; //длина X окна
  final static int FRAME_HEIGHT = 720; //длина Y окна
  private static final int INDEX_OF_LAYER_FLOAT_BUTTONS = 50;
  //FC FullScreen Modals
  private static final int INDEX_OF_MODAL_BG_FC = 0;
  private static final int INDEX_OF_MODAL_CONTENT_FC = 1;
  private static JFrame frame = new JFrame("Javagram");
  private static JLayeredPane lp = frame.getLayeredPane();
  private static Component floatComponents;
  private static UndecoratedResize ur;
  private static Logger l = Logger.getLogger("WindowHandler");


  public static synchronized void startFrame() {
    frame.setVisible(false);
    System.setProperty("awt.useSystemAAFontSettings", "on");
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        initFrame();
      }
    });


  }

  public static void setViewOnFrame(MainContract.IView view) {
    ur = new UndecoratedResize();
    frame.setContentPane(view.getMainPanel());
    repaintFrame();

  }

  private static void initFrame() {
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setUndecorated(true);
    frame.setLocationRelativeTo(null); //выравниваем окно по центру экрана
    //frame.setVisible(true);
  }

  public static Font getMainFont(int size) {
    //default font
    Font font = Font.getFont(Font.SANS_SERIF);
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, Configs.FONT_FILE_REGULAR)
          .deriveFont((float) size);

    } catch (FontFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return font;
  }

  public static void hideFrame() {
    frame.setVisible(false);
  }

  public static Font getMainFontBold(int size) {
    //default font
    Font font = Font.getFont(Font.SANS_SERIF);
    try {
      font = Font.createFont(Font.TRUETYPE_FONT, Configs.FONT_FILE_BOLD).deriveFont((float) size);
    } catch (FontFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return font;
  }


  public static void frameSetContent(JPanel p) {
    lp.removeAll();
    frame.setContentPane(p);
    frame.repaint();
    frame.setVisible(true);
  }

  public static void minimizeFrame() {
    frame.setState(JFrame.ICONIFIED);
  }

  public static void maximizeFrame() {
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
  }

  public static void normalizeFrame() {
    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    frame.setLocationRelativeTo(null);
  }

  public static void makeFrameResizable() {
    ur = new UndecoratedResize();
    frame.getRootPane().add(ur.createAnsShowGui(frame, (short) 800, (short) 600));
  }

  public static void resetFrame() {
    Container c = frame.getContentPane();
    lp.removeAll();
    frame.setContentPane(c);
  }

  public static void repaintFrame() {
    frame.repaint();
    frame.setVisible(true);
  }

  //set layered above content Pane, ONLY ONE MODAL PANEL SET!
  public static void setModalFullScreenPanel(JPanel layered, JPanel layeredBg) {
    JPanel c = (JPanel) frame.getContentPane();
    //удаляем все панели что были в Модальном слое, чтобы не было наслоения
    lp.removeAll();
    frame.setContentPane(c);
    showLayeredFloatButtons();
    //отступ чтобы не закрывать заголовок окна
    layeredBg.setBounds(0, 30, (int) getFrameSize().getWidth(), (int) getFrameSize().getHeight());
    //вставляем в JLayeredPane нужные формы
    //constraints управляем пордяком, чем больше значение тем выше элемент
    lp.add(layeredBg, JLayeredPane.MODAL_LAYER, INDEX_OF_MODAL_BG_FC);
    lp.add(layered, JLayeredPane.MODAL_LAYER + 1, INDEX_OF_MODAL_CONTENT_FC);

    //обновляем frame
    frame.repaint();
    frame.setVisible(true);

  }

  //set float buttons to frame
  public static void removeModalFullScreenPanel() {
    JPanel c = (JPanel) frame.getContentPane();
    lp.removeAll();
    frame.setContentPane(c);
    showLayeredFloatButtons();
    frame.repaint();
    frame.setVisible(true);
  }

  //set float buttons to frame
  public static void showLayeredFloatButtons() {
    if (floatComponents != null) {
      lp.add(floatComponents, JLayeredPane.MODAL_LAYER-1, INDEX_OF_LAYER_FLOAT_BUTTONS);
    } else {
      l.info("Не установлены плавающие компоненты формы! floatComponents не заданы!");
    }
  }

  //TODO: принимать массив элементов
  public static void setFloatComponents(Component c) {
    floatComponents = c;
  }

  //get layered Pane
  public static Dimension getFrameSize() {
    return frame.getSize();
  }

  public static Point getLocation() {
    return frame.getLocation();
  }

  public static void setLocation(int px, int py) {
    frame.setLocation(px, py);
  }

  public static void pack(){
    frame.pack();
  }
}
