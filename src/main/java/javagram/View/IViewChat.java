/**
 * Project Javagram Created by Shibkov Konstantin on 05.03.2019.
 */
package javagram.View;

    import javagram.Presenter.PrChat;

//view for window with phone input
public interface IViewChat extends IView {

  void setPresenter(PrChat presenter);

}
