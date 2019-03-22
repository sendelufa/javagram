/**
 * Project Javagram Created by Shibkov Konstantin on 22.03.2019.
 */
package javagram.WindowGUI;

import java.awt.Color;
import java.awt.Dimension;
import javagram.WindowGUI.components.ScrollBarUIActive;
import javagram.WindowGUI.components.ScrollBarUIInactive;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GUIHelper {

  public static void decorateScrollPane(JScrollPane scrollPane) {
    int width = 4;

    JScrollBar verticalScrollBar =  scrollPane.getVerticalScrollBar();
    verticalScrollBar.setUI(new ScrollBarUIActive());
    verticalScrollBar.setPreferredSize(new Dimension(width, Integer.MAX_VALUE));

    JScrollBar horizontalScrollBar =  scrollPane.getHorizontalScrollBar();
    horizontalScrollBar.setUI(new ScrollBarUIActive());
    horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, width));

    for (String corner : new String[] {ScrollPaneConstants.LOWER_RIGHT_CORNER, ScrollPaneConstants.LOWER_LEFT_CORNER,
        ScrollPaneConstants.UPPER_LEFT_CORNER, ScrollPaneConstants.UPPER_RIGHT_CORNER}) {
      JPanel panel = new JPanel();
      panel.setBackground(Color.white);
      scrollPane.setCorner(corner, panel);
    }
  }

  public static void decorateScrollBarActive(JScrollBar bar){
    bar.setUI(new ScrollBarUIActive());
  }

  public static void decorateScrollBarInactive(JScrollBar bar){
    bar.setUI(new ScrollBarUIInactive());
  }
}
