package javagram.WindowGUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Project Javagram
 * Created by Shibkov Konstantin on 26.12.2018.
 */

public class UndecoratedResize {

    private JFrame frame = new JFrame();

    class MainPanel extends JPanel {

        public MainPanel() {
            setBackground(Color.gray);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 600);
        }
    }

    class BorderPanel extends JPanel {

        private JLabel label;
        int pX, pY;

        public BorderPanel() {
            label = new JLabel(" X ");
            label.setOpaque(true);
            label.setBackground(Color.RED);
            label.setForeground(Color.WHITE);

            setBackground(Color.black);
            setLayout(new FlowLayout(FlowLayout.RIGHT));

            add(label);

            label.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    System.exit(0);
                }
            });
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    // Get x,y and store them
                    pX = me.getX();
                    pY = me.getY();

                }

                public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
                            frame.getLocation().y + me.getY() - pY);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,
                            frame.getLocation().y + me.getY() - pY);
                }
            });
        }
    }

    class OutsidePanel extends JPanel {

        public OutsidePanel() {
           /* setLayout(new BorderLayout());
            add(new MainPanel(), BorderLayout.CENTER);
            add(new BorderPanel(), BorderLayout.PAGE_START);
            setBorder(new LineBorder(Color.BLACK, 5));*/
        }
    }

    //change for Javagram
    public JPanel createAnsShowGui(Component c, short minWidth, short minHeight) {
        ComponentResizer cr = new ComponentResizer();
        cr.setMinimumSize(new Dimension(minWidth, minHeight));
        cr.setMaximumSize(new Dimension(1920, 1080));
        cr.registerComponent(c);
        cr.setSnapSize(new Dimension(10, 10));
        //frame.setUndecorated(true);
        JPanel p = new OutsidePanel();
        p.setVisible(false);
        return p;

    }
}
