package loginprototype.DB;

import loginprototype.DB.DAO.UserDAO;
import loginprototype.DB.Postgres.PostgresFactory;

/**
 * Classe abstraite singleton produisant différents DAO
 */
public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  /**
   * Retourne un DAO de User
   * @return
   */
  abstract public UserDAO getUserDAO();

  /**
   * Retourne l'instance de la classe implémentant AbstractFactory
   * @return
   */
  public static AbstractFactory createInstance() {
    return instance;
  }
}
