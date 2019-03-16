package javagram.View; /**
 * Project Javagram
 * Created by Shibkov Konstantin on 03.01.2019.
 */

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

public class ViewContactChangeProfile extends LayeredPaneBlackGlass {

    public ViewContactChangeProfile(Dimension frameDim, String title) throws IOException, FontFormatException {
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
        txtName.setText("Андрей");

        //Текстовое поле Фамилия
        JTextPane txtSurname = new JTextPane();
        txtSurname.setCaretColor(Color.WHITE);
        txtSurname.setForeground(Color.WHITE);
        txtSurname.setOpaque(false);
        txtSurname.setFont(WindowHandler.getMainFont(30));
        txtSurname.setPreferredSize(new Dimension(250, 40));
        txtSurname.setText("Петров");

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
