package javagram.View; /**
 * Project Javagram
 * Created by Shibkov Konstantin on 24.12.2018.
 */

import javagram.WindowGUI.WindowHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SetUserInfo {
    private JPanel mainPanel;
    private JButton btnMinimize;
    private JButton btnExit;
    private JPanel panelLogo;
    private JTextPane lbpDescPhone;
    private JButton btnSendCode;
    private JPanel pnlBtnSend;
    private JPanel panelHeadline;
    //Headline of Form
    private JPanel pnlBtnExit;
    private JPanel pnlBtnMinimize;
    private JPanel pnlIcoPhone;
    private JLabel lblBtnSend;
    private JLabel lblDescription;
    private JPanel pnUserPhoto;
    private JTextPane txtName;
    private JTextPane txtSurname;
    //Resources - Images
    private BufferedImage bg;
    private BufferedImage logo;
    private BufferedImage imgBtn;
    private BufferedImage imgHeadClose;
    private BufferedImage imgHeadMin;
    private BufferedImage imgUserPhotoEmpty;
    //inner params


    public SetUserInfo() {
        try {
            bg = ImageIO.read(new File("res/img/background.jpg"));
            logo = ImageIO.read(new File("res/img/logo-mini.png"));
            imgBtn = ImageIO.read(new File("res/img/button-background.png"));
            imgHeadClose = ImageIO.read(new File("res/img/icon-close.png"));
            imgHeadMin = ImageIO.read(new File("res/img/icon-hide.png"));
            imgUserPhotoEmpty = ImageIO.read(new File("res/img/UserPhotoEmpty.png"));
        } catch (IOException e) {
            System.err.println("Неудалось загрузить картинки!");
            e.printStackTrace();
        }

        //add Adapter to Drag window over the screen
        //Loader.MouseAdapterDrag(getPanelHeadline());

        //Set Fonts
            lblDescription.setFont(WindowHandler.getMainFont(14));
            lblBtnSend.setFont(WindowHandler.getMainFont(30));
            txtSurname.setFont(WindowHandler.getMainFont(36));
            txtName.setFont(WindowHandler.getMainFont(36));


        //change Layout to mainPanel fo Y axis position
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Add Listener to Headline Buttons
        pnlBtnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(1);
            }
        });
        pnlBtnMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                WindowHandler.minimizeFrame();
            }
        });

        //temp
        lblBtnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                WindowHandler.frameSetContent(new ViewChat().getMainPanel());

            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    //Custom UI components create
    private void createUIComponents() {


        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //Draw Image on panel
                g.drawImage(bg, 0, 0, 800, 600, null);
            }
        };

        panelLogo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(logo, 0, 0, null);
            }
        };

        btnSendCode = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgBtn, 0, 0, null);
            }
        };

        pnlBtnSend = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgBtn, 0, 3, null);
            }
        };

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

        pnUserPhoto = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imgUserPhotoEmpty, 0, 0, null);
            }
        };


    }


    public JPanel getPanelHeadline() {
        return panelHeadline;
    }
}
