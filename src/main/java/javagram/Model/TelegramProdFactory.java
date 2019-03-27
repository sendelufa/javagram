package javagram.Model;

import javagram.Configs;
import javagram.Log;
import javagram.MainContract.Repository;
import javagram.MainContract.RepositoryFactory;

public class TelegramProdFactory implements RepositoryFactory {

  private static Repository repository = null;

  @Override
  public Repository getModel() {
    if (repository == null) {
      if (Configs.REPOSITORY.equals("TLRepositoryTest")) {
        Log.info("RepositoryFactory: TLRepositoryTest");
        repository = TLRepositoryTest.getInstance();
      } else if (Configs.REPOSITORY.equals("TLProd")) {
        Log.info("RepositoryFactory: TLProd");
        repository = TLRepositoryProd.getInstance();
      }
    }
    return repository;
  }
}
