package togaether.BL.Facade;

import javafx.event.ActionEvent;
import togaether.BL.Model.*;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.NotificationDAO;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationFacade {
    private static NotificationFacade instance = new NotificationFacade();

    private Notification notification;

    public Notification getNotification() {
        return notification;
    }

    public static NotificationFacade getInstance(){return instance;}

    /**
     * Query all notifications of specific user (given in parameters) and return it
     * @param user
     * @return a list of notifications
     */
    public List<Notification> getAllNotificationsOfUser(User user){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();
        List<Notification> notifications = new ArrayList<>();
        try{
            notifications = notificationDB.findNotificationsByUserId(user.getId());
        }catch(SQLException e){
            System.out.println(e);
        }
        return notifications;
    }

    /**
     * Query the modification of a specific notification (given in parameters) in DB
     * @param notification
     */
    public void setNotification(Notification notification){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();

        try{
            notificationDB.updateNotification(notification);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Query the creation of a specific notification (given in parameters) in DB
     * @param notification
     */
    public void createNotification(Notification notification){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();

        try{
            notificationDB.createNotification(notification);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Query the supression of a specific notification (given in parameters) in DB
     * @param notification
     */
    public void deleteNotification(Notification notification){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();

        try{
            notificationDB.deleteNotificationById(notification.getId());
        }catch(SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Method that handle notification acceptance depending on its category
     * @param notification
     */
    public void acceptNotification(Notification notification, ActionEvent event){
        switch(notification.getCatNotification().getNameCatNotification()){
            case "friendInvitation":
                acceptFriendInvitation(notification);
                break;
            case "travelInvitation":
                try{
                    TravelFacade.getInstance().setTravel(TravelFacade.getInstance().findTravelById(notification.getSpecialId()));
                    SceneController.getInstance().switchToTravel(event);
                }catch(DBNotFoundException e){
                    System.out.println(e);
                }


                break;
            case "serverNotification":
                deleteNotification(notification);
                break;
            default:
                System.out.println("Category not found");

        }

    }

    /**
     * - Create a new Friend instance in database
     * - Delete previous notification
     * - Create new notification "serverNotification" to the user that made the previous invitation
     * @param notification
     */
    public void acceptFriendInvitation(Notification notification){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();
        try{
            //DB CALLS
            User other = new User(notification.getSpecialId());
            Friend friend = new Friend(UserFacade.getInstance().getConnectedUser(),other);
            FriendFacade.getInstance().createFriend(friend);
            notificationDB.deleteNotificationById(notification.getId());

            //Create serverNotification
            Notification secondNotification = new Notification(notification.getFrom(),
                    "L'utilisateur " + UserFacade.getInstance().getConnectedUser().getPseudo() + " a accepté votre demande d'ami",
                    notification.isAccept(),
                    NotificationCategory.createNotification("serverNotification"));
            notificationDB.createNotification(secondNotification);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * - Create a new Collaborateur instance in database
     * - Delete previous notification
     * - Create new notification "serverNotification" to the user that made the previous invitation
     * @param notification
     */
    public void acceptTravelInvitation(Notification notification){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();

        try{
            //Create second notification
            Notification secondNotification = new Notification(notification.getFrom(),
                    "L'utilisateur " + notification.getTo().getName() + " a accepté votre invitation au voyage",
                    notification.isAccept(),
                    NotificationCategory.createNotification("serverNotification"));

            //DB CALLS
            notificationDB.deleteNotificationById(notification.getId());
            //should call CollaboratorFacade to create the new Collaborator instance
            notificationDB.createNotification(secondNotification);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Method that handle notification refusing depending on its category
     * @param notification
     */
    public void refuseNotification(Notification notification){
        deleteNotification(notification);
    }

    /**
     * Query how many notifications a user has and return it
     * @param user
     * @return number of notification a user has in DB
     */
    public int getNbNotificationsByUserId(User user){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();
        int res = -1;

        try{
            res = notificationDB.getNbNotificationByUserId(user.getId());
        }catch (SQLException e){
            System.out.println(e);
        }
        return res;
    }

    /**
     * Query a specific number of notifications depending on a specific user_id and minimum_id of notification
     * @param user
     * @param limit
     * @param min
     * @return a list of these notifications (can be empty)
     */
    public List<Notification> getSpecificAmountOfNotificationsByUserIdAndStartingId(User user, int limit, int min){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();
        List<Notification> notifications = new ArrayList<>();

        try{
            notifications = notificationDB.getSpecificAmountOfNotificationsByUserIdAndStartingId(user.getId(),limit,min);
        }catch (SQLException e){
            System.out.println(e);
        }
        return notifications;
    }

    /**
     * Query a specific notification based on its id and return it
     * @param id
     * @return a specific notification
     */
    public Notification getNotificationById(int id){
        AbstractFactory fact = AbstractFactory.createInstance();
        NotificationDAO notificationDB = fact.getNotificationDAO();
        try{
            Notification notif = notificationDB.findNotificationById(id);
            return notif;
        }catch(SQLException e ){
            System.out.println(e);
        }
        return null;

    }

}
