package togaether.DB;

import togaether.BL.Model.Activity;

import java.sql.SQLException;
import java.util.List;

public interface ActivityDAO {
    /**
     * Query the creation of the actual activity in DB
     */
    void createActivity(Activity activity) throws SQLException;

    /**
     * Query the update of the actual activity in DB
     */
    void updateActivity(Activity activity) throws SQLException;

    /**
     * Query the deletion of the actual activity in DB
     */


    void updateActivityById(Activity activity, int id) throws SQLException;

        /**
        * Query the deleting of the actual activity in DB
        */
    void deleteActivity(Activity activity) throws SQLException;

    void deleteActivityById(int id) throws SQLException;

    //Find activity by id
    Activity findActivityById(int id) throws SQLException;

    //Find activity by name
    Activity findActivityByName(String name) throws SQLException;

    //Find all activities by travel id
    List<Activity> findAllActivitiesByTravelId(int travelId) throws SQLException;

    //Find all activities
    List<Activity> findAllActivities() throws SQLException;

}
