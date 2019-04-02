package javagram.View;

import java.io.File;
import javagram.Configs;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ViewUtils {

  public static String getFullNameInitiates(String userFirstName, String userLastName) {
    if (userFirstName == null || userLastName == null || userFirstName.equals("")) {
      return "n/a error";
    }
    boolean hasLastName = (userLastName != null && !userLastName.equals(""));
    return hasLastName ? userFirstName.substring(0, 1) + userLastName.substring(0, 1)
        : userFirstName.substring(0, 1);
  }

  public static File getPhotoFileChooser() {
    String[] extensions = {"jpg", "jpeg"};
    return FileChooser
        .chooseFile(new FileNameExtensionFilter("Image files (.jpg, .jpeg)", extensions));
  }
}

class FileChooser {

  static private FileNameExtensionFilter fileFilter;
  static private File fileChosen;


  static File chooseFile(FileNameExtensionFilter ff) {
    fileFilter = ff;
    //вызываем диалоговое окно для сохранения результата в файл
    JFileChooser fileChooser = new JFileChooser();
    //настройка выбора: только файлы
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setDialogTitle(Configs.TXT_CHOOSE_IMAGE);
    //настройка фильтра файлов
    fileChooser.setFileFilter(fileFilter);
    //вызываем диалоговое окно
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      fileChosen = fileChooser.getSelectedFile();
      return fileChosen;
    }

    return null;
  }

  //проверяем - если на конце файла расширение, если нет - добавляем
  static private File setFileExtension() {
    return fileChosen.getPath().matches("^.*\\." + fileFilter.getExtensions()[0] + "$") ? fileChosen
        :
            new File(fileChosen.getPath() + "." + fileFilter.getExtensions()[0]);
  }
}
