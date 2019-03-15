package javagram.View;

import javax.swing.JPanel;

public interface IView {

  //show various error
  void showError(String strError);

  void clearError();

  //show Loading Icon (animated)
  void showLoadingProcess();

  //hide Loading Icon (animated)
  void hideLoadingProcess();

  //main panel to show in frame
  JPanel getMainPanel();
}
