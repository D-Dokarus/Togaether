package togaether.DB.Postgres;

import togaether.BL.Model.Notification;
import togaether.BL.Model.NotificationCategory;
import togaether.BL.Model.User;
import togaether.DB.AbstractFactory;
import togaether.DB.NotificationDAO;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOPostgres implements NotificationDAO {
    PostgresFactory postgres;

    public NotificationDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    /**
     * Query the creation of a specific notification (given in parameters) in DB
     * @param notification
     */
    @Override
    public void createNotification(Notification notification) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "";
            if(notification.getFrom() != null && notification.getSpecialId() != 0){
                query = "INSERT INTO notification(to_user,from_user,content_notif,accept,cat_notif,special_id) VALUES(?,?,?,?,?,?);";
                try(PreparedStatement statement =
                            connection.prepareStatement(query);){
                    statement.setInt(1,notification.getTo().getId());
                    statement.setInt(2,notification.getFrom().getId());
                    statement.setString(3,notification.getContentNotif());
                    statement.setBoolean(4,notification.isAccept());
                    statement.setInt(5,notification.getCatNotification().getIdCatNotification());
                    statement.setInt(6,notification.getSpecialId());
                    statement.executeQuery();
                }
            }else if(notification.getFrom() == null){
                query = "INSERT INTO notification(to_user,content_notif,accept,cat_notif,special_id) VALUES(?,?,?,?,?);";
                try(PreparedStatement statement =
                            connection.prepareStatement(query);){
                    statement.setInt(1,notification.getTo().getId());
                    statement.setString(2,notification.getContentNotif());
                    statement.setBoolean(3,notification.isAccept());
                    statement.setInt(4,notification.getCatNotification().getIdCatNotification());
                    statement.setInt(5,notification.getSpecialId());
                    statement.executeUpdate();
                }
            }else if(notification.getSpecialId() == 0){
                query = "INSERT INTO notification(to_user,from_user,content_notif,accept,cat_notif) VALUES(?,?,?,?,?,?);";
                try(PreparedStatement statement =
                            connection.prepareStatement(query);){
                    statement.setInt(1,notification.getTo().getId());
                    statement.setInt(2,notification.getFrom().getId());
                    statement.setString(3,notification.getContentNotif());
                    statement.setBoolean(4,notification.isAccept());
                    statement.setInt(5,notification.getCatNotification().getIdCatNotification());
                    statement.executeUpdate();
                }
            }else if(notification.getSpecialId() == 0 && notification.getFrom() == null){
                query = "INSERT INTO notification(to_user,content_notif,accept,cat_notif) VALUES(?,?,?,?,?);";
                try(PreparedStatement statement =
                            connection.prepareStatement(query);){
                    statement.setInt(1,notification.getTo().getId());
                    statement.setString(2,notification.getContentNotif());
                    statement.setBoolean(3,notification.isAccept());
                    statement.setInt(4,notification.getCatNotification().getIdCatNotification());
                    statement.executeUpdate();
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    /**
     * Query the supression of a specific notification_id (given in parameters) in DB
     * @param id
     */
    @Override
    public void deleteNotificationById(int id) throws SQLException{
        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM notification WHERE notif_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }


    }

    /**
     * Query the finding of a specific notification depending on its id and return it if exist
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Notification findUsersRelatedNotificationById(int id) throws  SQLException{
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT notif_id, to_user, from_user, content_notif, accept, cat_notif, special_id, u1.user_name as u1_name, u1.user_surname as u1_surname, u1.user_pseudo as u1_pseudo, u1.user_country as u1_country, u2.user_name as u2_name, u2.user_surname as u2_surname, u2.user_pseudo as u2_pseudo, u2.user_country as u2_country, name_notification_cat FROM notification, public.user u1, public.user u2, notification_categories WHERE notif_id = ? and to_user = u1.user_id and from_user = u2.user_id and cat_notif = id_notification_cat; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    User to = new User(Integer.valueOf(resultSet.getString("to_user")),
                            resultSet.getString("u1_name"),
                            resultSet.getString("u1_surname"),
                            resultSet.getString("u1_pseudo"),
                            resultSet.getString("u1_country"));
                    User from = new User(Integer.valueOf(resultSet.getString("from_user")),
                            resultSet.getString("u2_name"),
                            resultSet.getString("u2_surname"),
                            resultSet.getString("u2_pseudo"),
                            resultSet.getString("u2_country"));
                    NotificationCategory category = NotificationCategory.createNotification(resultSet.getString("name_notification_cat"));

                    Notification notification = new Notification(resultSet.getInt("notif_id"),
                            to,
                            from,
                            resultSet.getString("content_notif"),
                            resultSet.getBoolean("accept"),
                            category,
                            resultSet.getInt("special_id"));
                    return notification;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    /**
     * Query the finding of a specific notification depending on its id and return it if exist
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Notification findNotificationById(int id) throws  SQLException{
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM notification, notification_categories WHERE notif_id = ? and cat_notif = id_notification_cat; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    User to = new User(resultSet.getInt("to_user"));
                    User from = new User(resultSet.getInt("from_user"));

                    NotificationCategory category = NotificationCategory.createNotification(resultSet.getString("name_notification_cat"));

                    Notification notification = new Notification(resultSet.getInt("notif_id"),
                            to,
                            from,
                            resultSet.getString("content_notif"),
                            resultSet.getBoolean("accept"),
                            category,
                            resultSet.getInt("special_id"));
                    return notification;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    /**
     * Query all notifications of specific user_id (given in parameters) and return it
     * @param id
     * @return a list of notifications
     */
    @Override
    public List<Notification> findNotificationsByUserId(int id) throws SQLException{
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM notification, notification_categories WHERE to_user = ? and cat_notif = id_notification_cat; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, id);
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<Notification> notifications = new ArrayList<>();
                    while(resultSet.next()){
                        NotificationCategory category = NotificationCategory.createNotification(resultSet.getString("name_notification_cat"));

                        Notification notification = new Notification(resultSet.getInt("notif_id"),
                                null,
                                null,
                                resultSet.getString("content_notif"),
                                resultSet.getBoolean("accept"),
                                category,
                                resultSet.getInt("special_id"));

                        notifications.add(notification);
                    }
                    return notifications;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    /**
     * Query the modification of a specific notification (given in parameters) in DB
     * Assuming that you have at least all fields of a notifications without from_user and special_id fields
     * @param notification
     * @throws SQLException
     */
    @Override
    public void updateNotification(Notification notification) throws SQLException{
        try(Connection connection = this.postgres.getConnection()){
            if(notification.getTo() != null){
                String query = "UPDATE notification SET to_user = ? , from_user = ? , content_notif = ? , accept = ? , cat_notif = ? , special_id = ? WHERE notif_id = ? ;";
                try(PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setInt(7,notification.getId());
                    statement.setInt(1,notification.getTo().getId());
                    if(notification.getFrom() != null){
                        statement.setInt(2,notification.getFrom().getId());
                    }else{
                        statement.setNull(2,Types.INTEGER);
                    }
                    statement.setString(3,notification.getContentNotif());
                    statement.setBoolean(4,notification.isAccept());
                    statement.setInt(5,notification.getCatNotification().getIdCatNotification());
                    if(notification.getSpecialId() != 0){
                        statement.setInt(6,notification.getSpecialId());
                    }else{
                        statement.setNull(6,Types.INTEGER);
                    }
                    statement.executeUpdate();
                }
            }else{
                String query = "UPDATE notification SET from_user = ? , content_notif = ? , accept = ? , cat_notif = ? , special_id = ? WHERE notif_id = ? ;";
                try(PreparedStatement statement = connection.prepareStatement(query)){
                    statement.setInt(6,notification.getId());
                    if(notification.getFrom() != null){
                        statement.setInt(1,notification.getFrom().getId());
                    }else{
                        statement.setNull(1,Types.INTEGER);
                    }
                    statement.setString(2,notification.getContentNotif());
                    statement.setBoolean(3,notification.isAccept());
                    statement.setInt(4,notification.getCatNotification().getIdCatNotification());
                    if(notification.getSpecialId() != 0){
                        statement.setInt(5,notification.getSpecialId());
                    }else{
                        statement.setNull(5,Types.INTEGER);
                    }
                    statement.executeUpdate();
                }
            }

        }
        finally{
            this.postgres.getConnection().close();
        }

    }

    /**
     * Query the number of notification a specific user has (by his id given in parameters) and return it.
     * @param id
     * @return
     * @throws SQLException
     */
    public int getNbNotificationByUserId(int id)throws SQLException{
        int res = -1;
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT count(*) FROM notification WHERE to_user = ?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,id);
                statement.executeQuery();
                try(ResultSet resultSet = statement.getResultSet()){
                    resultSet.next();
                    res = resultSet.getInt(1);
                    return res;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    /**
     * Query a specific number of notifications depending on a specific user_id and minimum_id of notification
     * @param user_id
     * @param limit
     * @param min_notif_id
     * @return
     * @throws SQLException
     */
    public List<Notification> getSpecificAmountOfNotificationsByUserIdAndStartingId(int user_id, int limit,int min_notif_id) throws SQLException {
        ArrayList<Notification> notifications = new ArrayList<>();
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT notif_id, to_user, from_user, content_notif, accept, cat_notif, special_id, name_notification_cat, u1.user_name as u1_name, u1.user_surname as u1_surname, u1.user_pseudo as u1_pseudo, u1.user_country as u1_country, u2.user_name as u2_name, u2.user_surname as u2_surname, u2.user_pseudo as u2_pseudo, u2.user_country as u2_country FROM public.user as u1, notification_categories, notification LEFT JOIN public.user as u2 ON from_user = u2.user_id WHERE to_user = ? and notif_id > ? and cat_notif = id_notification_cat AND u1.user_id = to_user ORDER BY notif_id ASC LIMIT ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,user_id);
                statement.setInt(2,min_notif_id);
                statement.setInt(3,limit);
                statement.executeQuery();

                try(ResultSet resultSet = statement.getResultSet()){

                    while(resultSet.next()){
                        User to = new User(resultSet.getInt("to_user"),resultSet.getString("u1_name"),resultSet.getString("u1_surname"),resultSet.getString("u1_pseudo"),resultSet.getString("u1_country"));
                        User from = null;
                        if(resultSet.getInt("from_user") != 0 ){
                           from = new User(resultSet.getInt("from_user"),resultSet.getString("u2_name"),resultSet.getString("u2_surname"),resultSet.getString("u2_pseudo"),resultSet.getString("u2_country"));
                            System.out.println(from.getId() + " " +from.getPseudo() + " " + from.getName() + " " + from.getSurname()+ " " + to.getCountry());

                        }
                        System.out.println(to.getId() + " " +to.getPseudo() + " " + to.getName() + " " + to.getSurname()+ " " + to.getCountry());
                        NotificationCategory category = NotificationCategory.createNotification(resultSet.getString("name_notification_cat"));
                        System.out.println("hehe");
                        Notification notification = null;
                        notification = new Notification(resultSet.getInt("notif_id"),
                                    to,
                                    from,
                                    resultSet.getString("content_notif"),
                                    resultSet.getBoolean("accept"),
                                    category,
                                    resultSet.getInt("special_id"));
                        notifications.add(notification);
                    }
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
        return notifications;
    }

    public static void main(String args[]){


        AbstractFactory af = AbstractFactory.createInstance();
        NotificationDAO ndaop = af.getNotificationDAO();



        /*
        //TEST L'INSERT D'UNE NOTIF
        User u1 = new User(4,"Maxime","maxime@gmail.com","mdp");
        User u2 = new User(1,"Dorian","dorian@gmail.com","mdp");
        NotificationCategory n1 = NotificationCategory.createNotification("friendInvitation");
        NotificationCategory n2 = NotificationCategory.createNotification("serverNotification");
        for(int i = 0; i< 30; i++){
            Notification t3 = new Notification(u1,"MessageServer" + i,true,n2);
            try{
                ndaop.createNotification(t3);
            }catch (SQLException e){
                System.out.println(e);
            }
        }*/



        /*
        //TEST DU FIND BY ID
        try{
            Notification result = ndaop.findNotificationById(7);
        }catch(SQLException e){
            System.out.println(e);
        }*/

        /*
        //TEST DU DELETE BY NOTIF_ID
        try{
            ndaop.deleteNotificationById(10);
        }catch (SQLException e){
            System.out.println(e);
        }*/

        /*
        //TEST DU FIND BY USER_ID
        try{
            List<Notification> notifications = ndaop.findNotificationsByUserId(4);

            for(Notification notif : notifications){
                System.out.println(notif);
            }
        }catch(SQLException e){
            System.out.println(e);
        }*/

        /*
        //TEST DU UPDATE BY NOTIF_ID
        try{
            List<Notification> notifications = ndaop.findNotificationsByUserId(4);
            Notification not = notifications.get(0);
            not.setContentNotif("JE VIENS DE CHANGER LE CONTENT NOTIF");
            ndaop.updateNotification(not);
        }catch(SQLException e){
            System.out.println(e);
        }*/





    }
}
