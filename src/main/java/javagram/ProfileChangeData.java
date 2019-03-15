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

public class ProfileChangeData extends LayeredPaneBlackGlass {

    public ProfileChangeData(Dimension frameDim, String title)
        throws IOException, FontFormatException {
        super(frameDim, title);


        //Панель с основной формой для редактирования
        // является FlowLayout внутри которой две формы
        JPanel pnlData = new JPanel();
        pnlData.setLayout(new FlowLayout());
        pnlData.setPreferredSize(new Dimension(450, 300));
        pnlData.setOpaque(false);

        //панель с фотографией и двумя иконками
        JPanel pnlImageBlock = new JPanel();
        pnlImageBlock.setLayout(new BoxLayout(pnlImageBlock, BoxLayout.Y_AXIS));
        pnlImageBlock.setOpaque(false);

        //панель - фотография c использованием переопределеного класса JPanel
        MyPanelBgImage pnlImage = new MyPanelBgImage("res/img/UserPhotoEmpty.png");
        pnlImage.setLayout(new FlowLayout());
        pnlImage.setPreferredSize(new Dimension(200, 200));
        pnlImage.setOpaque(false);

        //иконка редактирования c использованием переопределеного класса JPanel
        MyPanelBgImage pnlEdit = new MyPanelBgImage("res/img/icon-edit.png");
        pnlEdit.setLayout(new FlowLayout());
        pnlEdit.setPreferredSize(new Dimension(20, 20));
        pnlEdit.setOpaque(false);

        //иконка удаления c использованием переопределеного класса JPanel
        MyPanelBgImage pnlDelete = new MyPanelBgImage("res/img/icon-trash.png");
        pnlDelete.setLayout(new FlowLayout());
        pnlDelete.setPreferredSize(new Dimension(20, 20));
        pnlDelete.setOpaque(false);


        //панель - кнопки под фотографиями
        JPanel pnlImageButtons = new JPanel();
        pnlImageButtons.setLayout(new FlowLayout());
        pnlImageButtons.setPreferredSize(new Dimension(200, 30));
        pnlImageButtons.setOpaque(false);


        //панель с полями имя и фамиилия
        JPanel pnlNameSur = new JPanel();
        pnlNameSur.setLayout(new BoxLayout(pnlNameSur, BoxLayout.Y_AXIS));
        pnlNameSur.setOpaque(false);

        //Текстовое поле Имя
        JTextPane txtName = new JTextPane();
        txtName.setCaretColor(Color.WHITE);
        txtName.setForeground(Color.WHITE);
        txtName.setOpaque(false);
        txtName.setFont(WindowHandler.getMainFont(30));
        txtName.setPreferredSize(new Dimension(250, 40));
        txtName.setText("Имя");

        //Текстовое поле Фамилия
        JTextPane txtSurname = new JTextPane();
        txtSurname.setCaretColor(Color.WHITE);
        txtSurname.setForeground(Color.WHITE);
        txtSurname.setOpaque(false);
        txtSurname.setFont(WindowHandler.getMainFont(30));
        txtSurname.setPreferredSize(new Dimension(250, 40));
        txtSurname.setText("Фамилия");

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

        //Блок выйти
        JPanel pnlBtnExit = new JPanel();
        pnlBtnExit.setLayout(new FlowLayout());
        pnlBtnExit.setOpaque(false);

        //текст номера телефона
        JLabel lblPhoneNumber = new JLabel("+7915 000-00-00");
        lblPhoneNumber.setOpaque(false);
        lblPhoneNumber.setFont(WindowHandler.getMainFont(24));
        lblPhoneNumber.setForeground(Color.WHITE);

        JLabel lblExit = new JLabel(" выйти ");
        lblExit.setOpaque(false);
        lblExit.setFont(WindowHandler.getMainFont(24));
        System.setProperty("TitleColor", "0X00b5ea");
        lblExit.setForeground(Color.getColor("TitleColor"));


        pnlImageButtons.add(pnlEdit);
        pnlImageButtons.add(pnlDelete);

        pnlImageBlock.add(pnlImage);
        pnlImageBlock.add(pnlImageButtons);

        pnlNameSur.add(txtName);
        pnlNameSur.add(new MyUnderLineText());
        pnlNameSur.add(new MyTransparentSpace(50));
        pnlNameSur.add(txtSurname);
        pnlNameSur.add(new MyUnderLineText());
        pnlNameSur.add(new MyTransparentSpace(50));

        pnlData.add(pnlImageBlock);
        pnlData.add(pnlNameSur);
        pnlData.add(pnlBtnSave);

        mainContent.add(pnlData);

        pnlBtnExit.add(lblPhoneNumber);
        pnlBtnExit.add(lblExit);

        pnlFooter.add(pnlBtnBack, BorderLayout.WEST);
        pnlFooter.add(pnlBtnExit, BorderLayout.EAST);

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
