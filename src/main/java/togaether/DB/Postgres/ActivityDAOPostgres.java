package togaether.DB.Postgres;

import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.BL.Model.Activity;
import togaether.DB.TravelDAO;
import togaether.DB.ActivityDAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAOPostgres implements ActivityDAO {

    PostgresFactory postgres;

    public ActivityDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    @Override
    public void createActivity(Activity activity) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO activity (name_activity, description_activity, travel_id, address_activity, date_start, date_end, price_activity) VALUES (?,?,?,?,?,?,?)";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, activity.getNameActivity());
                statement.setString(2, activity.getDescriptionActivity());
                statement.setInt(3, activity.getTravelAssociated_id());
                statement.setString(4, activity.getAddressActivity());
                statement.setDate(5, (Date) activity.getDateStart());
                statement.setDate(6, (Date) activity.getDateEnd());
                statement.setDouble(7, activity.getPriceActivity());
                statement.executeUpdate();
            }
        }
    }

    //Penser à faire comme TravelUpdateController
    @Override
    public void updateActivityById(Activity activity, int activity_id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "UPDATE activity SET name_activity = ?, description_activity = ?, address_activity = ?, date_start = ?, date_end = ?, price_activity = ? WHERE activity_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, activity.getNameActivity());
                statement.setString(2, activity.getDescriptionActivity());
                statement.setString(3, activity.getAddressActivity());
                statement.setDate(4, (Date) activity.getDateStart());
                statement.setDate(5, (Date) activity.getDateEnd());
                statement.setDouble(6, activity.getPriceActivity());
                statement.setInt(7, (activity_id));
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void updateActivity(Activity activity) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "UPDATE activity SET name_activity = ?, description_activity = ?, address_activity = ?, date_start = ?, date_end = ?, price_activity = ? WHERE activity_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, activity.getNameActivity());
                statement.setString(2, activity.getDescriptionActivity());
                statement.setString(3, activity.getAddressActivity());
                statement.setDate(4, (Date) activity.getDateStart());
                statement.setDate(5, (Date) activity.getDateEnd());
                statement.setDouble(6, activity.getPriceActivity());
                statement.setInt(7, (activity.getIdActivity()));
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteActivityById(int activity_id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM activity WHERE activity_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, activity_id);
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteActivity(Activity activity) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM activity WHERE activity_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, activity.getIdActivity());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public Activity findActivityById(int activity_id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM activity WHERE activity_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, activity_id);
                try(ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next()) {
                        return new Activity(
                                resultSet.getInt("activity_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("name_activity"),
                                resultSet.getString("description_activity"),
                                resultSet.getString("address_activity"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getDouble("price_activity")
                        );
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Activity findActivityByName(String name_activity) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM activity WHERE name_activity = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, name_activity);
                try(ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next()) {
                        return new Activity(
                                resultSet.getInt("activity_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("name_activity"),
                                resultSet.getString("description_activity"),
                                resultSet.getString("address_activity"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getDouble("price_activity")
                        );
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Activity> findAllActivities() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        try (Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM activity";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                try(ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        activities.add(new Activity(
                                resultSet.getInt("activity_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("name_activity"),
                                resultSet.getString("description_activity"),
                                resultSet.getString("address_activity"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getDouble("price_activity")
                        ));
                    }
                }
            }
        }
        return activities;
    }

    @Override
    public List<Activity> findAllActivitiesByTravelId(int travel_id) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        try (Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM activity WHERE travel_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, travel_id);
                try(ResultSet resultSet = statement.executeQuery();) {

                    while (resultSet.next()) {
                        Activity activity = new Activity(
                                resultSet.getInt("activity_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("name_activity"),
                                resultSet.getString("description_activity"),
                                resultSet.getString("address_activity"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getDouble("price_activity")
                        );
                        activities.add(activity);
                    }
                    return activities;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }





}
