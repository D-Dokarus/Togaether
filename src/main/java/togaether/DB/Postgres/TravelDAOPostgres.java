package togaether.DB.Postgres;

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
            String query = "INSERT INTO travel(owner,name_travel,description_travel,date_start,date_end,is_archive) VALUES(?,?,?,?,?,?);";
            try(PreparedStatement statement = connection.prepareStatement(query);){
                statement.setInt(1,travel.getOwner().getId());
                statement.setString(2,travel.getNameTravel());
                statement.setString(3,travel.getDescriptionTravel());
                statement.setDate(4, (Date) travel.getDateStart());
                statement.setDate(5, (Date) travel.getDateEnd());
                statement.setBoolean(6,travel.isArchive());
                statement.executeUpdate();
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
            String query = "SELECT * FROM travel INNER JOIN public.user u ON travel.owner=u.user_id WHERE travel_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();


                    /*User owner = new User(Integer.valueOf(resultSet.getString("owner")),
                            resultSet.getString(9),
                            resultSet.getString(10),
                            resultSet.getString(11));*/
                    User user = new User(resultSet.getInt("owner"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));

                    Travel travel = new Travel(resultSet.getInt("travel_id"),
                                user,
                                resultSet.getString("name_travel"),
                                resultSet.getString("description_travel"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getBoolean("is_archive"));
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
                                resultSet.getString("name_travel"),
                                resultSet.getString("description_travel"),
                                resultSet.getDate("date_start"),
                                resultSet.getDate("date_end"),
                                resultSet.getBoolean("is_archive"));

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
            String query = "UPDATE travel SET  name_travel = ? , description_travel = ? , date_start = ? , date_end = ? WHERE travel_id = ? ;";
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
                statement.setInt(1,travel.getOwner().getId());
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
            String query = "UPDATE travel SET is_archive = ? WHERE travel_id = ? ;";
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
            String query = "UPDATE travel SET is_archive = ? WHERE travel_id = ? ;";
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
