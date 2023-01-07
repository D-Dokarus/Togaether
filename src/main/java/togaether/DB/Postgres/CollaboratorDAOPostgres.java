package togaether.DB.Postgres;

import togaether.BL.Model.*;

import togaether.BL.Model.Collaborator;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;
import togaether.DB.MessageDAO;
import togaether.DB.NotificationDAO;

import javax.xml.transform.Result;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;

public class CollaboratorDAOPostgres implements CollaboratorDAO {
  PostgresFactory postgres;

  public CollaboratorDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }
  @Override
  public Collaborator getCollaboratorByUserIdAndTravelId(int user_id, int travel_id) throws SQLException {
    Collaborator collaborator = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM collaborator WHERE user_id =? AND travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.setInt(2,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
             collaborator = new Collaborator(resultSet.getInt("collaborator_id"), new Travel(resultSet.getInt("travel_id")), new User(resultSet.getInt("user_id")), resultSet.getString("collaborator_name"));
          }
        }
      }
    }
    return collaborator;
  }

  @Override
  public int createCollaborator(Collaborator collaborator) throws SQLException {
    Collaborator returned_collaborator = null;
    try(Connection connection = this.postgres.getConnection()) {
      if (collaborator.getUser() == null) {
        String query = "INSERT INTO collaborator(collaborator_name,travel_id) VALUES(?,?) RETURNING collaborator_id;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setString(1, collaborator.getName());
          statement.setInt(2, collaborator.getTravel().getIdTravel());
          statement.execute();
          ResultSet result = statement.getResultSet();
          result.next();
          return result.getInt(1);
        }
      } else {
        String query = "INSERT INTO collaborator(collaborator_name,travel_id,user_id) VALUES(?,?,?) RETURNING collaborator_id;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setString(1, collaborator.getName());
          statement.setInt(2, collaborator.getTravel().getIdTravel());
          statement.setInt(3, collaborator.getUser().getId());
          statement.execute();
          ResultSet result = statement.getResultSet();
          result.next();
          return result.getInt(1);
        }


      }
    }
  }

  @Override
  public void deleteCollaborator(Collaborator collaborator) throws SQLException {
    try(Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM collaborator WHERE collaborator_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,collaborator.getId());
        statement.executeUpdate();
      }
    }
  }

  @Override
  public void deleteAllCollaboratorByTravel(Travel travel) throws SQLException {
    try(Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM collaborator WHERE travel_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,travel.getIdTravel());
        statement.executeUpdate();
      }
    }
  }

  @Override
  public void updateCollaborator(Collaborator collaborator) throws SQLException {
    try(Connection connection = this.postgres.getConnection()) {
      if (collaborator.getUser() != null && collaborator.getUser().getId() != 0l) {
        String query = "UPDATE collaborator SET travel_id = ?, user_id = ?, collaborator_name = ? WHERE collaborator_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setInt(1, collaborator.getTravel().getIdTravel());
          statement.setInt(2, collaborator.getUser().getId());
          statement.setString(3, collaborator.getName());
          statement.setInt(4, collaborator.getId());
          statement.executeUpdate();
        }
      } else {
        String query = "UPDATE collaborator SET travel_id = ?, collaborator_name = ? WHERE collaborator_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
          statement.setInt(1, collaborator.getTravel().getIdTravel());
          statement.setString(2, collaborator.getName());
          statement.setInt(3, collaborator.getId());
          statement.executeUpdate();
        }

      }
    }
  }

  @Override
  public List<Collaborator> findCollaboratorByTravel(Travel travel) throws SQLException {
    ArrayList<Collaborator> collaborators = new ArrayList<>();
    try(Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM collaborator LEFT JOIN public.user u ON collaborator.user_id=u.user_id WHERE travel_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,travel.getIdTravel());
        statement.executeQuery();
        ResultSet result = statement.getResultSet();
        while(result.next()){
          User user = new User(result.getInt("user_id"), result.getString("user_name"),result.getString("user_surname"), result.getString("user_pseudo"), result.getString("user_email"),result.getString("user_country"));
          Collaborator collab = new Collaborator(result.getInt("collaborator_id"),travel,user,result.getString("collaborator_name"));
          collaborators.add(collab);
        }
        return collaborators;
      }
    }
  }

  @Override
  public List<Collaborator> findCollaboratorByUser(User user) throws SQLException {

    ArrayList<Collaborator> collaborators = new ArrayList<>();
    try(Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM travel INNER JOIN collaborator ON collaborator.travel_id = travel.travel_id INNER JOIN public.user u ON u.user_id = travel.owner WHERE collaborator.user_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,user.getId());
        statement.executeQuery();
        ResultSet result = statement.getResultSet();
        while(result.next()){
          User user_to_return = new User(result.getInt("user_id"), result.getString("user_name"),result.getString("user_surname"), result.getString("user_pseudo"), result.getString("user_email"),result.getString("user_country"));
          Travel travel = new Travel(result.getInt("travel_id"),user_to_return,result.getString("name_travel"),result.getString("description_travel"),result.getDate("date_start"),result.getDate("date_end"),result.getBoolean("is_archive"));
          Collaborator collab = new Collaborator(result.getInt("collaborator_id"),travel,user,result.getString("collaborator_name"));
          collaborators.add(collab);
        }
        return collaborators;
      }
    }
  }

  @Override
  public Collaborator findCollaboratorById(int id) throws SQLException {
    try(Connection connection  = this.postgres.getConnection()){
      String query = "SELECT * FROM collaborator LEFT JOIN public.user u ON u.user_id = collaborator.user_id  WHERE collaborator_id = ? ;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,id);
        statement.executeQuery();

        ResultSet result = statement.getResultSet();
        result.next();

        User user = new User(result.getInt("user_id"), result.getString("user_name"),result.getString("user_surname"), result.getString("user_pseudo"), result.getString("user_email"),result.getString("user_country"));
        Travel travel = new Travel(result.getInt("travel_id"),null,null,null,null,null,false);
        Collaborator collaborator = new Collaborator(result.getInt("collaborator_id"),travel,user,result.getString("collaborator_name"));

        return collaborator;
      }
    }
  }

  @Override
  public void deleteAllColaboratorByUser(User user) throws SQLException {
    try(Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM collaborator WHERE user_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,user.getId());
        statement.executeQuery();
      }
    }
  }

  @Override
  public void setAllUser_IdToNullByUser(User user)throws SQLException{
    try(Connection connection = this.postgres.getConnection()){
      String query = "UPDATE collaborator SET user_id = null WHERE user_id = ?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,user.getId());
        statement.executeQuery();
      }
    }
  }

  @Override
  public List<Collaborator> findCollaboratorNotChosenByTravel(Travel travel) throws SQLException{
    ArrayList<Collaborator> collaborators = new ArrayList<>();
    try(Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM collaborator WHERE travel_id = ? AND user_id IS NULL;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,travel.getIdTravel());
        statement.executeQuery();
        ResultSet result = statement.getResultSet();
        while(result.next()){
          Collaborator collab = new Collaborator(result.getInt("collaborator_id"),travel,result.getString("collaborator_name"));
          collaborators.add(collab);
        }
        return collaborators;
      }
    }
  }


  public static void main(String args[]){
      AbstractFactory af = AbstractFactory.createInstance();
      CollaboratorDAO cdaop = af.getCollaboratorDAO();

    //On Test CreateCollaborator
    /*
    Travel travel = new Travel(1,null,null,null,null,null,false);
    Collaborator collaborator = new Collaborator(travel,"TEST_NAME");
    try{
      int x = cdaop.createCollaborator(collaborator);
      System.out.println(x);
    }catch(SQLException e){
      System.out.println(e);
    }*/


    //On test deleteCollaborator
    /*
    Travel travel = new Travel(5,null,null,null,null,null,false);
    Collaborator collaborator = new Collaborator(5,travel,"TEST_NAME");
    try{
      cdaop.deleteCollaborator(collaborator);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test deleteAllCollaboratorByTravel
     /*
    Travel travel = new Travel(5,null,null,null,null,null,false);
    Collaborator collaborator = new Collaborator(5,travel,"TEST_NAME");

    try{
      cdaop.createCollaborator(collaborator);
      cdaop.createCollaborator(collaborator);
      cdaop.createCollaborator(collaborator);
      cdaop.createCollaborator(collaborator);
    }catch(SQLException e){
      System.out.println(e);
    }*/
    /*
    try{
      cdaop.deleteAllCollaboratorByTravel(travel);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test UpdateCollaborator()
    /*
    Travel travel = new Travel(1,null,null,null,null,null,false);
    Collaborator collaborator = new Collaborator(4,travel,"Modified_TEST_NAME");
    try{
      cdaop.updateCollaborator(collaborator);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test findCollaboratorByTravel()
    /*Travel travel = new Travel(1,null,null,null,null,null,false);
    Collaborator collaborator = new Collaborator(travel,"TEST_NAME");
    try{
      List<Collaborator> collaborators = cdaop.findCollaboratorByTravel(travel);
      System.out.println(collaborators);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test findCOllaboratorByUserId()
    /*Travel travel = new Travel(1,null,null,null,null,null,false);
    User user = new User(1,null,null,null);
    try{
      List<Collaborator> collaborators = cdaop.findCollaboratorByUser(user);
      System.out.println(collaborators);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test findCollaboratorById()
    /*try{
      Collaborator collaborator = cdaop.findCollaboratorById(1);
      System.out.println(collaborator);
    }catch(SQLException e){
      System.out.println(e);
    }*/

    //On test le delete ALL by User
    /*
    User user = new User(4,null,null,null);
    try{
      cdaop.deleteAllColaboratorByUser(user);
    }catch (SQLException e){
      System.out.println(e);
    }
     */

    //On test le setAllUser_IdToNullByUser
    /*
    User user = new User(4,null,null,null);
    try{
      cdaop.setAllUser_IdToNullByUser(user);
    }catch (SQLException e){
      System.out.println(e);
    }
    */
  }
}
