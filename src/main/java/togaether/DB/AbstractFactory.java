package togaether.DB;

import togaether.DB.Postgres.PostgresFactory;

/**
 * Classe abstraite singleton produisant différents DAO
 */
public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  /**
   * Retourne un DAO de User
   * @return UserDAO
   */
  abstract public UserDAO getUserDAO();
  /**
   * Retourne un DAO de Message
   * @return MessageDAO
   */
  abstract public MessageDAO getMessageDAO();
  abstract public TrophyDAO getTrophyDAO();
  /**
   * Retourne un DAO de Notification
   * @return NotificationDAO
   */
  abstract public NotificationDAO getNotificationDAO();
  /**
   * Retourne un DAO de Travel
   * @return TravelDAO
   */
  abstract public TravelDAO getTravelDAO();
  /**
   * Retourne un DAO de Collaborator
   * @return CollaboratorDAO
   */
  abstract public CollaboratorDAO getCollaboratorDAO();
  /**
   * Retourne un DAO de Friend
   * @return FriendDAO
   */
  abstract public FriendDAO getFriendDAO();
  /**
   * Retourne un DAO de Itinerary
   * @return ItineraryDAO
   */
  abstract public ItineraryDAO getItinerary();

    /**
     * Retourne un DAO de Document
     *
     * @return DocumentDAO
     */
    abstract public DocumentDAO getDocumentDAO();

    /**
     * Retourne l'instance de la classe implémentant AbstractFactory
     *
     * @return
     */
    public static AbstractFactory createInstance() {
        return instance;
    }


}
