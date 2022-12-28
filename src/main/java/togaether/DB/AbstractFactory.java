package togaether.DB;

import togaether.DB.Postgres.PostgresFactory;

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
  abstract public MessageDAO getMessageDAO();

  /**
   * Retourne l'instance de la classe implémentant AbstractFactory
   * @return
   */
  public static AbstractFactory createInstance() {
    return instance;
  }
}
