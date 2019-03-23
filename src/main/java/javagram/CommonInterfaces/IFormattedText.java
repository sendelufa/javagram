package javagram.CommonInterfaces;

import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public interface IFormattedText {
  //format phone text input field
  static void setTextFieldMask(JFormattedTextField field, String mask, char placeholder) {
    MaskFormatter maskFormatter;
    try {
      maskFormatter = new MaskFormatter(mask);
      maskFormatter.setPlaceholder(null);
      maskFormatter.setPlaceholderCharacter(placeholder);
      field.setFormatterFactory(new DefaultFormatterFactory(maskFormatter));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
