package togaether.DB.Postgres;

import togaether.BL.Model.Notification;
import togaether.BL.Model.NotificationCategory;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.DB.TravelDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDAOPostgres implements TravelDAO {

    PostgresFactory postgres;

    public TravelDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    @Override
    public void createTravel(Travel travel) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO travel(owner,nameTravel,descriptionTravel,dateStart,dateEnd,isArchive) VALUES(?,?,?,?,?,?);";
            try(PreparedStatement statement = connection.prepareStatement(query);){
                statement.setInt(1,travel.getOwner().getIdUser());
                statement.setString(2,travel.getNameTravel());
                statement.setString(3,travel.getDescriptionTravel());
                statement.setDate(4, (Date) travel.getDateStart());
                statement.setDate(5, (Date) travel.getDateEnd());
                statement.setBoolean(6,travel.isArchive());
                statement.executeQuery();
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteTravelById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM travel WHERE travel_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }

    }

    @Override
    public Travel findTravelById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT FROM travel WHERE travel_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    User owner = new User(Integer.valueOf(resultSet.getString("owner")), // à vérifier
                                            resultSet.getString(9),
                                            resultSet.getString(10),
                                            resultSet.getString(11));
                    Travel travel = new Travel(resultSet.getInt("travel_id"),
                                                owner,
                                                resultSet.getString("nameTravel"),
                                                resultSet.getString("descriptionTravel"),
                                                resultSet.getDate("dateStart"),
                                                resultSet.getDate("dateEnd"),
                                                resultSet.getBoolean("isArchive"));
                    return travel;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<Travel> findTravelsByUserId(int Id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM travel WHERE owner = ? ; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<Travel> travels = new ArrayList<>();
                    while(resultSet.next()){
                        Travel travel = new Travel(resultSet.getInt("travel_id"),
                                null,
                                resultSet.getString("nameTravel"),
                                resultSet.getString("descriptionTravel"),
                                resultSet.getDate("dateStart"),
                                resultSet.getDate("dateEnd"),
                                resultSet.getBoolean("isArchive"));

                        travels.add(travel);
                    }
                    return travels;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<Travel> findTravelsByUserIdAndString(int Id, String str) throws SQLException {
        return null;
    }

    @Override
    public void updateTravel(Travel travel) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE travel SET  nameTravel = ? , descriptionTravel = ? , dateStart = ? , dateEnd = ? WHERE travel_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(5,travel.getIdTravel());
                statement.setString(1,travel.getNameTravel());
                statement.setString(2,travel.getDescriptionTravel());
                statement.setDate(3, (Date) travel.getDateStart());
                statement.setDate(4, (Date) travel.getDateEnd());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }

    }

    @Override
    public void updateOwnerTravel(Travel travel) throws SQLException{
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE travel SET owner = ? WHERE travel_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,travel.getOwner().getIdUser());
                statement.setInt(2,travel.getIdTravel());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void archiveTravel(Travel travel) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE travel SET isArchive = ? WHERE travel_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setBoolean(1,true);
                statement.setInt(2,travel.getIdTravel());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }

    }

    @Override
    public void unarchiveTravel(Travel travel) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE travel SET isArchive = ? WHERE travel_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setBoolean(1,false);
                statement.setInt(2,travel.getIdTravel());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }

    }
}
