package javagram; /**
 * Project Javagram
 * Created by Shibkov Konstantin on 03.01.2019.
 */

import javagram.View.formElements.MyPanelBgImage;
import javagram.View.formElements.MyTransparentSpace;
import javagram.View.formElements.MyUnderLineText;

import javagram.WindowGUI.WindowHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ProfileAddContact extends LayeredPaneBlackGlass {

    public ProfileAddContact(Dimension frameDim, String title) throws IOException, FontFormatException {
        super(frameDim, title);


        //Панель с основной формой для редактирования
        // является FlowLayout внутри которой две формы
        JPanel pnlData = new JPanel();
        pnlData.setLayout(new FlowLayout());
        pnlData.setPreferredSize(new Dimension(450, 300));
        pnlData.setOpaque(false);

        //панель основная
        JPanel pnlMainBlock = new JPanel();
        pnlMainBlock.setLayout(new BoxLayout(pnlMainBlock, BoxLayout.Y_AXIS));
        pnlMainBlock.setOpaque(false);

        JLabel lblDesc = new JLabel("<html>Введите номер мобильного<br>телефона пользователя</html>");
        lblDesc.setOpaque(false);
        lblDesc.setFont(WindowHandler.getMainFont(18));
        lblDesc.setForeground(Color.WHITE);
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
        lblFindUserName.setPreferredSize(new Dimension(400, 20));

        //панель с полями имя и фамиилия
        JPanel pnlNameSur = new JPanel();
        pnlNameSur.setLayout(new BoxLayout(pnlNameSur, BoxLayout.Y_AXIS));
        pnlNameSur.setOpaque(false);

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


        pnlNameSur.add(new MyTransparentSpace(50));
        pnlNameSur.add(txtPhoneNumber);
        pnlNameSur.add(new MyUnderLineText());
        pnlNameSur.add(new MyTransparentSpace(50));



        pnlMainBlock.add(lblDesc);
        pnlMainBlock.add(pnlNameSur);
        pnlMainBlock.add(lblFindUserTitle);
        pnlMainBlock.add(lblFindUserName);
        pnlMainBlock.add(pnlBtnSave);

        pnlData.add(pnlMainBlock);

        mainContent.add(pnlData);

        pnlFooter.add(pnlBtnBack, BorderLayout.WEST);

        //компонуем все в основную панель
        panel.add(pnlFooter, BorderLayout.SOUTH);

        //temp
        pnlBtnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                WindowHandler.removeModalFullScreenPanel();

            }
        });



    }


}
