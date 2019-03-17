/**
 * Project Javagram Created by Shibkov Konstantin on 15.03.2019.
 */
package javagram.View.formElements;

import java.awt.Dimension;
import java.awt.Graphics;
import javagram.Configs;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class MessageItem extends JFrame {

  private JPanel pnlMsgOutTop;
  private JPanel pnlMsgOutBottom;
  private JPanel pnlMsgTip;
  private JPanel mainPanel;
  private JTextPane txtMessage;
  private JLabel txtMessage1;
  private JPanel pnlMessageText;
  private JPanel pnlMessage;
  private JLabel lblMessageText;

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public MessageItem(){
      txtMessage.setText("Когда теряет равновесие\n"
          + "твоё сознание усталое,\n"
          + "когда ступеньки этой лестницы\n"
          + "уходят из под ног,\n"
          + "как палуба,\n"
          + "когда плюёт на человечество\n"
          + "твоё ночное одиночество, —\n"
          + "ты можешь\n"
          + "размышлять о вечности");
      txtMessage.revalidate();
    //getTxtMessage().setPreferredSize(new Dimension(287, (int) getTxtMessage().getPreferredSize().getHeight()));
    //getTxtMessage().revalidate();
    System.out.println(getTxtMessage().getSize());
    getPnlMessageText().setPreferredSize(
        new Dimension((int) getTxtMessage().getPreferredSize().getWidth(),
            (int) getTxtMessage().getPreferredSize().getHeight()));
    getPnlMessage().setPreferredSize(
        new Dimension((int) getTxtMessage().getPreferredSize().getWidth()+24,
            (int) getTxtMessage().getPreferredSize().getHeight()+34));
  }

  public JTextPane getTxtMessage() {
    return txtMessage;
  }

  public JPanel getPnlMessageText() {
    return pnlMessageText;
  }

  public JPanel getPnlMessage() {
    return pnlMessage;
  }

  public JLabel getLblMessageText() {
    return lblMessageText;
  }

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
