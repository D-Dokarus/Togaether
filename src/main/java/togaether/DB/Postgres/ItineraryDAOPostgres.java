package togaether.DB.Postgres;

import togaether.BL.Model.Itinerary;
import togaether.BL.Model.TransportCategory;
import togaether.DB.ItineraryDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItineraryDAOPostgres implements ItineraryDAO {

    PostgresFactory postgres;

    public ItineraryDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    @Override
    public int createItinerary(Itinerary itinerary) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO itinerary(travel,name,date,index_before,category, description, hour, index_after) VALUES(?,?,?,?,?,?,?,?) RETURNING itinerary_id;";
            try(PreparedStatement statement = connection.prepareStatement(query);){
                statement.setInt(1,itinerary.getTravel());
                statement.setString(2,itinerary.getName());
                statement.setDate(3, (Date) itinerary.getDateItinerary());
                statement.setInt(4,itinerary.getIndexBefore());
                statement.setInt(5, itinerary.getCategory());
                statement.setString(6, itinerary.getDescription());
                statement.setString(7, itinerary.getHour());
                statement.setInt(8,itinerary.getIndexAfter());
                statement.execute();
                ResultSet result = statement.getResultSet();
                result.next();
                return result.getInt(1);
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteItineraryById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM itinerary WHERE itinerary_id=?;";
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
    public Itinerary findItineraryById(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM itinerary WHERE itinerary_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();

                    Itinerary itinerary = new Itinerary(resultSet.getInt("itinerary_id"),
                            resultSet.getInt("travel"),
                            resultSet.getString("name"),
                            resultSet.getDate("date"),
                            resultSet.getInt("index_before"),
                            resultSet.getInt("category"),
                            resultSet.getString("description"),
                            resultSet.getString("hour"),
                            resultSet.getInt("index_after"));
                    return itinerary;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<Itinerary> findItinerariesByTravelId(int Id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM itinerary i INNER JOIN travel ON travel.travel_id=i.travel WHERE i.travel = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<Itinerary> itineraries = new ArrayList<>();
                    while(resultSet.next()){
                        Itinerary itinerary = new Itinerary(resultSet.getInt("itinerary_id"),
                                resultSet.getInt("travel"),
                                resultSet.getString("name"),
                                resultSet.getDate("date"),
                                resultSet.getInt("index_before"),
                                resultSet.getInt("category"),
                                resultSet.getString("description"),
                                resultSet.getString("hour"),
                                resultSet.getInt("index_after"));

                        itineraries.add(itinerary);
                    }
                    return itineraries;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateItinerary(Itinerary itinerary) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE itinerary SET  name = ? , date = ? , description = ?, hour =? WHERE itinerary_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(5,itinerary.getItinerary_id());
                statement.setString(1,itinerary.getName());
                statement.setDate(2, (Date) itinerary.getDateItinerary());
                statement.setString(3,itinerary.getDescription());
                statement.setString(4,itinerary.getHour());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateIndexBeforeItineraryById(Integer id, Integer indexBefore) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE itinerary SET  index_before = ? WHERE itinerary_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(2,id);
                statement.setInt(1, indexBefore);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateIndexAfterItineraryById(Integer id, Integer indexAfter) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE itinerary SET  index_after = ? WHERE itinerary_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(2,id);
                statement.setInt(1, indexAfter);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateIndexItinerary(Itinerary itinerary) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE itinerary SET  index_before = ?, index_after = ? WHERE itinerary_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(3,itinerary.getItinerary_id());
                statement.setInt(1,  itinerary.getIndexBefore());
                statement.setInt(2,  itinerary.getIndexAfter());
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteItinerariesByTravelId(int Id) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM itinerary WHERE travel=?;";
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
    public String findNameCatTransportById(int Id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT nameTransportCat FROM transport_category cat WHERE cat_transport_id = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    return resultSet.getString("nameTransportCat");
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public int findIdCatTransportByName(String name) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT cat_transport_id FROM transport_category cat WHERE nameTransportCat = ?; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setString(1, name);
                try(ResultSet resultSet = statement.executeQuery()){
                    return resultSet.getInt("cat_transport_id");
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<TransportCategory> findAllCatTransport() throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM transport_category ; ";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                try(ResultSet resultSet = statement.executeQuery()){
                    ArrayList<TransportCategory> categories = new ArrayList<>();
                    while(resultSet.next()){
                        TransportCategory transportCategory = new TransportCategory(resultSet.getInt("cat_transport_id"), resultSet.getString("nameTransportCat"));
                        categories.add(transportCategory);
                    }
                    return categories;
                }
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void switchIndexFor2ItinerariesById(int A,int B) throws SQLException { // Pour A B
        try(Connection connection = this.postgres.getConnection()){
            connection.setAutoCommit(false);
            String query1 = "UPDATE itinerary SET  index_before = ?, index_after = ? WHERE itinerary_id = ? ;";
            String query2 = "UPDATE itinerary SET  index_before = ?, index_after = ? WHERE itinerary_id = ? ;";

            PreparedStatement statement1 = connection.prepareStatement(query1); // A
            statement1.setInt(1, B);
            statement1.setInt(2, -1);
            statement1.setInt(3, A);
            statement1.executeUpdate();
            statement1.close();

            PreparedStatement statement2 = connection.prepareStatement(query2); // B
            statement2.setInt(1, -1);
            statement2.setInt(2, A);
            statement2.setInt(3, B);
            statement2.executeUpdate();
            statement2.close();

            connection.commit();

        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void switchIndexBeforeFor3ItinerariesById(int A,int B,int C) throws SQLException { // Pour A B C qui se suivent pour changement A-B avec A début de liste

        try(Connection connection = this.postgres.getConnection()){
            connection.setAutoCommit(false);
            String query1 = "UPDATE itinerary SET  index_before = ?, index_after = ? WHERE itinerary_id = ? ;";
            String query2 = "UPDATE itinerary SET  index_before = ?, index_after = ? WHERE itinerary_id = ? ;";
            String query3 = "UPDATE itinerary SET  index_before = ? WHERE itinerary_id = ? ;";

            PreparedStatement statement1 = connection.prepareStatement(query1); // A
            statement1.setInt(1, B);
            statement1.setInt(2, C);
            statement1.setInt(3, A);
            statement1.executeUpdate();
            statement1.close();

            PreparedStatement statement2 = connection.prepareStatement(query2); // B
            statement2.setInt(1, -1);
            statement2.setInt(2, A);
            statement2.setInt(3, B);
            statement2.executeUpdate();
            statement2.close();

            PreparedStatement statement3 = connection.prepareStatement(query3); // C
            statement3.setInt(1, A);
            statement3.setInt(2, C);
            statement3.executeUpdate();
            statement3.close();

            connection.commit();

        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void switchIndexAfterFor3ItinerariesById(int A,int B,int C) throws SQLException { // Pour A B C qui se suivent pour changement A-B avec A début de liste

        try(Connection connection = this.postgres.getConnection()){
            connection.setAutoCommit(false);
            String query1 = "UPDATE itinerary SET index_after = ? WHERE itinerary_id = ? ;";
            String query2 = "UPDATE itinerary SET index_before = ?, index_after = ? WHERE itinerary_id = ? ;";
            String query3 = "UPDATE itinerary SET index_before = ?, index_after = ? WHERE itinerary_id = ? ;";

            PreparedStatement statement1 = connection.prepareStatement(query1); // A
            statement1.setInt(1, C);
            statement1.setInt(2, A);
            statement1.executeUpdate();
            statement1.close();

            PreparedStatement statement2 = connection.prepareStatement(query2); // B
            statement2.setInt(1, C);
            statement2.setInt(2, -1);
            statement2.setInt(3, B);
            statement2.executeUpdate();
            statement2.close();

            PreparedStatement statement3 = connection.prepareStatement(query3); // C
            statement3.setInt(1, A);
            statement3.setInt(2, B);
            statement3.setInt(3, C);
            statement3.executeUpdate();
            statement3.close();

            connection.commit();

        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void switchIndexFor4ItinerariesById(int A,int B,int C, int D) throws SQLException { // Pour A B C D qui se suivent pour changement B-C
        try(Connection connection = this.postgres.getConnection()){
            connection.setAutoCommit(false);
            String query1 = "UPDATE itinerary SET index_after = ? WHERE itinerary_id = ? ;"; // A
            String query2 = "UPDATE itinerary SET index_before = ?, index_after = ? WHERE itinerary_id = ? ;"; // B
            String query3 = "UPDATE itinerary SET index_before = ?, index_after = ? WHERE itinerary_id = ? ;"; // C
            String query4 = "UPDATE itinerary SET index_before = ? WHERE itinerary_id = ? ;"; // D

            PreparedStatement statement1 = connection.prepareStatement(query1); // A
            statement1.setInt(1, C); // A prends pour after C
            statement1.setInt(2, A);
            statement1.executeUpdate();
            statement1.close();

            PreparedStatement statement2 = connection.prepareStatement(query2); // B
            statement2.setInt(1, C); // B prends pour before C
            statement2.setInt(2, D);  // B prends pour after D
            statement2.setInt(3, B);
            statement2.executeUpdate();
            statement2.close();

            PreparedStatement statement3 = connection.prepareStatement(query3); // C
            statement3.setInt(1, A); // C prends pour before A
            statement3.setInt(2, B); // C prends pour after B
            statement3.setInt(3, C);
            statement3.executeUpdate();
            statement3.close();

            PreparedStatement statement4 = connection.prepareStatement(query4); // D
            statement4.setInt(1, B); // D prends pour before B
            statement4.setInt(2, D);
            statement4.executeUpdate();
            statement4.close();

            connection.commit();

        }
        finally{
            this.postgres.getConnection().close();
        }
    }

}
