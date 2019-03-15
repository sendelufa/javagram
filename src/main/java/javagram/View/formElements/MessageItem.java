/**
 * Project Javagram Created by Shibkov Konstantin on 15.03.2019.
 */
package javagram.View.formElements;

import java.awt.Graphics;
import javagram.Configs;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class MessageItem {

  private JPanel pnlMsgOutTop;
  private JPanel pnlMsgOutBottom;
  private JPanel pnlMsgTip;
  private JPanel mainPanel;
  private JTextPane sdfsdfsdfSdfsdfDsfDsfTextPane;

  //Custom UI components create
  private void createUIComponents() {
    pnlMsgOutTop = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_OUTGOING_OUT_TOP, 0, 0, null);
      }
    };

    pnlMsgOutBottom = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_OUTGOING_OUT_BOTTOM, 0, 0, null);
      }
    };

    pnlMsgTip = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_OUTGOING_TIP, 0, 20, null);
      }
    };

  }
}
