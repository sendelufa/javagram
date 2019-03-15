/**
 * Project Javagram
 * Created by Shibkov Konstantin on 03.01.2019.
 */
package javagram.View.formElements;

        import javax.swing.*;
        import java.awt.*;

//класс с панелью для подчеркивания текста
public class MyTransparentSpace extends JPanel {
    public MyTransparentSpace(int height){
        super();
        setPreferredSize(new Dimension(1,height));
        setOpaque(false);
    }
}
