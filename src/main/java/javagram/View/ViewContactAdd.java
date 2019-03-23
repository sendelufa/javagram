package javagram.View; /**
 * Project Javagram Created by Shibkov Konstantin on 03.01.2019.
 */

import javagram.Log;
import javagram.MainContract.IPresenter;
import javagram.MainContract.IView;
import javagram.View.formElements.LayeredPaneBlackGlass;
import javagram.View.formElements.MyPanelBgImage;
import javagram.View.formElements.MyTransparentSpace;
import javagram.View.formElements.MyUnderLineText;

import javagram.WindowGUI.WindowHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ViewContactAdd extends LayeredPaneBlackGlass implements IView {

  private Dimension frameDim;
  private int frameWidth, frameHeight;
  private JPanel mainPanel;

  public ViewContactAdd(Dimension frameDim) throws IOException, FontFormatException {
    super(frameDim);
    this.frameDim = frameDim;
    frameWidth = this.frameDim.width;
    frameHeight = this.frameDim.height;
    initViewGUI();
  }

  private void initViewGUI() {
    //Панель с основной формой для редактирования
    // является FlowLayout внутри которой две формы
    mainPanel = new JPanel();
    mainPanel.setLayout(new FlowLayout());
    mainPanel.setPreferredSize(new Dimension(450, 300));
    mainPanel.setOpaque(false);

    //панель основная
    JPanel pnlMainBlock = new JPanel();
    pnlMainBlock.setLayout(new BoxLayout(pnlMainBlock, BoxLayout.Y_AXIS));
    //pnlMainBlock.setPreferredSize(new Dimension(500, 500));
    Log.info(pnlMainBlock.getPreferredSize().toString());
    pnlMainBlock.setOpaque(false);

    JLabel lblDesc = new JLabel("<html>Введите номер мобильного<br>телефона пользователя</html>");
    lblDesc.setOpaque(true);
    lblDesc.setFont(WindowHandler.getMainFont(18));
    lblDesc.setForeground(Color.WHITE);
    lblDesc.setHorizontalAlignment(JLabel.CENTER);
    lblDesc.setPreferredSize(new Dimension(400, 100));

    JLabel lblFindUserTitle = new JLabel("Найден пользователь:");
    lblFindUserTitle.setOpaque(false);
    lblFindUserTitle.setFont(WindowHandler.getMainFont(10));
    lblFindUserTitle.setForeground(Color.cyan);
    lblFindUserTitle.setPreferredSize(new Dimension(400, 20));

    JLabel lblFindUserName = new JLabel("Екатерина Смирнова");
    lblFindUserName.setOpaque(false);
    lblFindUserName.setFont(WindowHandler.getMainFont(18));
    lblFindUserName.setForeground(Color.WHITE);
    lblFindUserName.setPreferredSize(new Dimension(250, 50));

    //панель с вертикальным BoxLayout содержащий все элементы
    JPanel pnlElementsGroup = new JPanel();
    pnlElementsGroup.setLayout(new BoxLayout(pnlElementsGroup, BoxLayout.Y_AXIS));
    pnlElementsGroup.setOpaque(false);

    //Текстовое поле Фамилия
    JTextPane txtPhoneNumber = new JTextPane();
    txtPhoneNumber.setCaretColor(Color.WHITE);
    txtPhoneNumber.setForeground(Color.WHITE);
    txtPhoneNumber.setOpaque(false);
    txtPhoneNumber.setFont(WindowHandler.getMainFont(30));
    txtPhoneNumber.setPreferredSize(new Dimension(250, 40));
    txtPhoneNumber.setText("+7-999-123-45-67");

    //Кнопка Сохранить
    MyPanelBgImage pnlBtnSave = new MyPanelBgImage("res/img/button-save.png");
    pnlBtnSave.setLayout(new FlowLayout());
    pnlBtnSave.setPreferredSize(new Dimension(340, 70));
    pnlBtnSave.setOpaque(false);

    //
    //Панели внизу формы
    JPanel pnlFooter = new JPanel();
    pnlFooter.setOpaque(false);
    pnlFooter.setLayout(new BorderLayout());
    pnlFooter.setPreferredSize(new Dimension((int) frameDim.getWidth(), 50));

    //Кнопка Назад
    MyPanelBgImage pnlBtnBack = new MyPanelBgImage("res/img/icon-back.png");
    pnlBtnBack.setLayout(new FlowLayout());
    pnlBtnBack.setPreferredSize(new Dimension(35, 35));
    pnlBtnBack.setOpaque(false);

    pnlElementsGroup.add(new MyTransparentSpace(50));
    pnlElementsGroup.add(txtPhoneNumber);
    pnlElementsGroup.add(new MyUnderLineText());
    pnlElementsGroup.add(new MyTransparentSpace(50));

    pnlMainBlock.add(lblDesc);
    pnlMainBlock.add(pnlElementsGroup);
    pnlMainBlock.add(lblFindUserTitle);
    pnlMainBlock.add(lblFindUserName);
    pnlMainBlock.add(pnlBtnSave);

    mainPanel.add(pnlMainBlock);

    mainContent.add(mainPanel);

    pnlFooter.add(pnlBtnBack, BorderLayout.WEST);

    //компонуем все в основную панель
    panel.add(pnlFooter, BorderLayout.SOUTH);

    //Back to main panel
    pnlBtnBack.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);

        WindowHandler.removeModalFullScreenPanel();

      }
    });


  }

  @Override
  public void setPresenter(IPresenter presenter) {

  }

  @Override
  public void showError(String strError) {

  }

  @Override
  public void showInfo(String strError) {

  }

  @Override
  public void clearError() {

  }

  @Override
  public void showLoadingProcess() {

  }

  @Override
  public void hideLoadingProcess() {

  }

  @Override
  public JPanel getMainPanel() {
    return getContent();
  }
}
