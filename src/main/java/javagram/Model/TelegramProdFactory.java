/**
 * Project Javagram Created by Shibkov Konstantin on 20.03.2019.
 */
package javagram.Model;


import java.util.logging.Logger;
import javagram.Configs;
import javagram.MainContract.Repository;
import javagram.MainContract.RepositoryFactory;

public class TelegramProdFactory implements RepositoryFactory {
  private static Logger log = Logger.getLogger(TelegramProdFactory.class.getName());
  private static Repository repository;
  @Override
  public Repository getModel() {

    if (Configs.REPOSITORY.equals("TLRepositoryTest")) {
      log.info("RepositoryFactory: TLRepositoryTest");
      return TLRepositoryTest.getInstance();
    }
    else if (Configs.REPOSITORY.equals("TLProd")){
      log.info("RepositoryFactory: TLProd");
      return TLRepositoryProd.getInstance();
    }
    return null;
  }
}
