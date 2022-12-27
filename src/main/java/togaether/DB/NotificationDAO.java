package togaether.DB;

import togaether.BL.Model.Notification;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDAO {
    void createNotification(Notification notification) throws SQLException;
    void deleteNotificationById(int id) throws SQLException;
    Notification findNotificationById(int id) throws SQLException;
    List<Notification> findNotificationsByUserId(int id) throws SQLException;
    void updateNotification(Notification notification) throws SQLException;
    public int getNbNotificationByUserId(int id)throws SQLException;
    public List<Notification> getSpecificAmountOfNotificationsByUserIdAndStartingId(int user_id, int limit,int min_notif_id) throws SQLException;
}
