package togaether.DB;

import togaether.DB.Postgres.PostgresFactory;

/**
 * Classe abstraite singleton produisant différents DAO
 */
public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  /**
   * Return a DAO of User
   * @return UserDAO
   */
  abstract public UserDAO getUserDAO();
  /**
   * Return a DAO of Message
   * @return MessageDAO
   */
  abstract public MessageDAO getMessageDAO();
  abstract public TrophyDAO getTrophyDAO();
  /**
   * Return a DAO of Notification
   * @return NotificationDAO
   */
  abstract public NotificationDAO getNotificationDAO();
  /**
   * Return a DAO of Travel
   * @return TravelDAO
   */
  abstract public TravelDAO getTravelDAO();
  /**
   * Return a DAO of Collaborator
   * @return CollaboratorDAO
   */
  abstract public CollaboratorDAO getCollaboratorDAO();
  /**
   * Return a DAO of Friend
   * @return FriendDAO
   */
  abstract public FriendDAO getFriendDAO();

  /**
   * Return a DAO of Activity
   * @return ActivityDAO
   */
  abstract public ActivityDAO getActivityDAO();

  /**
   * Return a DAO of Itinerary
   * @return ItineraryDAO
   */
  abstract public ItineraryDAO getItineraryDAO();

  /**
   * Return a DAO of Itinerary
   * @return ItineraryDAO
   */
  abstract public BudgetDAO getBudgetDAO();

  /**
   * Return a DAO of Friend
   * @return FriendDAO
   */
  abstract public ExpenseDAO getExpenseDAO();

    /**
     * Return a DAO of Document
     *
     * @return DocumentDAO
     */
    abstract public DocumentDAO getDocumentDAO();

    /**
     * return the instance of the implemented class AbstractFactory
     *
     * @return
     */
    public static AbstractFactory createInstance() {
        return instance;
    }


}
