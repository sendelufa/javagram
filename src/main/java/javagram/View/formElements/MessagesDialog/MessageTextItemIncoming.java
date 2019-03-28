/**
 * Project Javagram Created by Shibkov Konstantin on 15.03.2019.
 */
package javagram.View.formElements.MessagesDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import javagram.CommonInterfaces.IHumanableDate;
import javagram.Configs;
import javagram.WindowGUI.WindowHandler;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessageTextItemIncoming implements IMessageItemDialog, IHumanableDate {

  private static Font f = WindowHandler.getMainFont(14);
  private JPanel pnlMsgOutTop;
  private JPanel pnlMsgOutBottom;
  private JPanel pnlMsgTip;
  private JPanel mainPanel;
  private JTextArea txtMessage;
  private JPanel pnlMessageText;
  private JPanel pnlMessage;
  private JPanel pnlMsgOutTopWest;
  private JPanel pnlMsgOutTopEast;
  private JPanel pnlTip;
  private JPanel pnlMsgOutBottomWest;
  private JPanel pnlMsgOutBottomEast;
  private JLabel lblMessageDate;
  private JPanel rootPanel;

  private int maxLineLength;

  public MessageTextItemIncoming(String text, int date) {
    getTxtMessage().setFont(f);
    resizeMessageTextPanels(text);
    lblMessageDate.setText(IHumanableDate.convertDate(date));
 }

  private int countWrappedLinesInString(String text, JTextArea textArea) {
    AttributedString attrText = new AttributedString(text);
    //font for textArea and AttributedString must be the same
    attrText.addAttribute(TextAttribute.FONT, f);
    FontRenderContext frc = textArea.getFontMetrics(textArea.getFont())
        .getFontRenderContext();

    AttributedCharacterIterator charIt = attrText.getIterator();
    LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
    float formatWidth = (float) textArea.getPreferredSize().width;
    lineMeasurer.setPosition(charIt.getBeginIndex());

    int numberLines = 0;
    while (lineMeasurer.getPosition() < charIt.getEndIndex()) {
      lineMeasurer.nextLayout(formatWidth);
      numberLines++;
    }
    return numberLines;

  }

  private int countWrappedLines(JTextArea textArea) {
    int countNewLine = 0;
    maxLineLength = 0;
    String[] strings = textArea.getText().split("\n");
    for (String s : strings) {
      if (s.equals("")) {
        countNewLine++;
      } else {
        if (s.length() > maxLineLength) {
          maxLineLength = s.length();
        }
        countNewLine += countWrappedLinesInString(s, textArea);
      }
    }
    return countNewLine;
  }

  protected void resizeMessageTextPanels(String text) {
    getTxtMessage().setText(text);
    getTxtMessage().setLineWrap(true);
    getTxtMessage().setWrapStyleWord(true);

    int wrappedLines = countWrappedLines(getTxtMessage());
    int recalculatedHeight =
        getTxtMessage().getFontMetrics(getTxtMessage().getFont()).getHeight() * wrappedLines;

    int widthOfTextArea = getTxtMessage().getPreferredSize().width;
    if (maxLineLength < 34){
      widthOfTextArea = maxLineLength * 9;}
    getTxtMessage().setPreferredSize(
        new Dimension(widthOfTextArea, recalculatedHeight));

    //resize other panel to fit JTextArea
    getPnlMessageText().setPreferredSize(
        new Dimension(widthOfTextArea + 20, getTxtMessage().getPreferredSize().height));
    getPnlMessage().setPreferredSize(
        new Dimension(widthOfTextArea + 20,getTxtMessage().getPreferredSize().height + 34));
  }

  //Custom UI components create
  private void createUIComponents() {
    pnlMsgOutTopWest = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_INCOMING_OUT_TOP_WEST, 0, 0, null);
      }
    };

    pnlMsgOutTopEast = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_INCOMING_OUT_TOP_EAST, 0, 0, null);
      }
    };

    pnlMsgOutBottomEast = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_INCOMING_OUT_BOTTOM_EAST, 0, 0, null);
      }
    };

    pnlTip = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw Image on panel
        g.drawImage(Configs.IMG_MESSAGE_INCOMING_TIP, 0, 9, null);
      }
    };

  }

  @Override
  public JPanel getMainPanel() {
    return rootPanel;
  }

  public JTextArea getTxtMessage() {
    return txtMessage;
  }

  public JPanel getPnlMessageText() {
    return pnlMessageText;
  }

  public JPanel getPnlMessage() {
    return pnlMessage;
  }
}
