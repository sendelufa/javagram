package javagram.View.interfaces;

import javagram.Presenter.interfaces.IPresenter;
import javax.swing.JPanel;

public interface IView {

  //main method init View
  void setPresenter(IPresenter presenter);

  //show various error
  void showError(String strError);

  //show various info
  void showInfo(String strError);

  void clearError();

  //show Loading Icon (animated)
  void showLoadingProcess();

  //hide Loading Icon (animated)
  void hideLoadingProcess();

  //main panel to show in frame
  JPanel getMainPanel();
}
