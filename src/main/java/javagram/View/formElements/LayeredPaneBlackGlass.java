package javagram.View.formElements; /**
 * Project Javagram
 * Created by Shibkov Konstantin on 03.01.2019.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javagram.WindowGUI.WindowHandler;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

//create BlackGlass panel without content
//Boxlayout with transparent Headline in NORTH
abstract public class LayeredPaneBlackGlass {
    protected MyGlassPanel panelBg; //skeleton
    protected JPanel panel;
    protected JPanel mainContent = new JPanel(); //content with main.java.formElements
    private JLabel lblTitle = new JLabel(); //title of content
    private BufferedImage imgBlackGlass;

    public LayeredPaneBlackGlass(Dimension frameDim, String title) {

        //Bg panel for glass effect
        panelBg = new MyGlassPanel();
        panelBg.setSize(frameDim);
        panelBg.setOpaque(false);

        panelBg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });

        //Panel with content
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setSize(frameDim); // Size is needed here, as there is no layout in lp


        //Headline
        JPanel panelNorthTransp = new JPanel();
        panelNorthTransp.setLayout(new FlowLayout());
        panelNorthTransp.setPreferredSize(new Dimension(800, 130));
        panelNorthTransp.setOpaque(false);

        //Title
        lblTitle.setPreferredSize(new Dimension((int)frameDim.getWidth(), 100));
        lblTitle.setSize(new Dimension((int)frameDim.getWidth(), 100));
        lblTitle.setFont(WindowHandler.getMainFont(36));
        lblTitle.setText(title);
        lblTitle.setVerticalAlignment(JLabel.BOTTOM);
        lblTitle.setHorizontalTextPosition(JLabel.CENTER);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setOpaque(false);
        System.setProperty("TitleColor", "0X00b5ea");
        lblTitle.setForeground(Color.getColor("TitleColor"));

        mainContent.setOpaque(false);//set layout for content
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        panelNorthTransp.add(lblTitle);
        //construct layouts in skeleton panel
        panel.add(panelNorthTransp, BorderLayout.NORTH);
        panel.add(mainContent, BorderLayout.CENTER);


    }

    //get JPanel where content will setup
    public JPanel getContent() {
        return panel;
    }

    //get BgPanel with semitransparent background
    public JPanel getBgPanel() {
        return panelBg;
    }
}
