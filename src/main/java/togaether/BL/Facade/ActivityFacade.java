package togaether.BL.Facade;


import togaether.BL.Model.Activity;
import togaether.BL.Model.Travel;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ActivityFacade {

    private Activity activity = null;

    static private ActivityFacade instance = new ActivityFacade();

    public static ActivityFacade getInstance() {
        return instance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    /**
     * Query the creation of the actual activity in DB
     */
    public void createActivity(Activity activity) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ActivityDAO activityDB = fact.getActivityDAO();
        try {
            activityDB.createActivity(activity);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the update of the actual activity in DB
     */
    public void updateActivity(Activity activity) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ActivityDAO activityDB = fact.getActivityDAO();
        try {
            activityDB.updateActivity(activity);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the update of the activity in DB with an id
     */
    public void updateActivityById(Activity activity, int activity_id) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ActivityDAO activityDB = fact.getActivityDAO();
        try {
            activityDB.updateActivityById(activity, activity_id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }


    /**
     * Query the deletion of the actual activity in DB
     */
    public void deleteActivity(Activity activity) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ActivityDAO activityDB = fact.getActivityDAO();
        try {
            activityDB.deleteActivity(activity);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the finding of the actual activity in DB
     */
    public List<Activity> findActivitiesByTravelId(int id) {
        ArrayList<Activity> liste = new ArrayList<>();
        ActivityDAO activityDB = AbstractFactory.createInstance().getActivityDAO();
        try {
            liste = (ArrayList<Activity>) activityDB.findAllActivitiesByTravelId(id);
        } catch (SQLException e) {}
        return liste;
    }




}
