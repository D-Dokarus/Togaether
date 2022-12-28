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
  /**
   * Retourne un DAO de Notification
   * @return
   */
  abstract public NotificationDAO getNotificationDAO();
  /**
   * Retourne un DAO de Travel
   * @return
   */
  abstract public TravelDAO getTravelDAO();

  /**
   * Retourne l'instance de la classe implémentant AbstractFactory
   * @return
   */
  public static AbstractFactory createInstance() {
    return instance;
  }
}
