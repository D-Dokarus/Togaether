package togaether.DB.Postgres;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Message;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.DB.MessageDAO;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class MessageDAOPostgres implements MessageDAO {


  PostgresFactory postgres;

  public MessageDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public void insertMessage(int travel_id, int collaborator_id, String message_content) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO message(travel_id, collaborator_id, message_content) VALUES(?,?,?);";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setInt(1,travel_id);
        statement.setInt(2,collaborator_id);
        statement.setString(3, message_content);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public List<Message> findMessagesByCollaboratorId(int id) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM message m INNER JOIN collaborator c ON m.collaborator_id=c.collaborator_id WHERE m.collaborator_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), "Lau", "lau@SE.com", "1234");
            Travel travel = new Travel(resultSet.getInt("travel_id"),user,"test_name","test_desc",new Date(), new Date(),false);
            Collaborator collaborator = new Collaborator(resultSet.getInt("collaborator_id"), travel, user, resultSet.getString("collaborator_name"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), collaborator, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
          }
        }
      }
    }
    return messages;
  }
  @Override
  public List<Message> findMessagesByUserId(int id) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM message m INNER JOIN collaborator c ON m.collaborator_id=c.collaborator_id INNER JOIN public.user u ON c.user_id=u.user_id WHERE u.user_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), "Lau", "lau@SE.com", "1234");
            Travel travel = new Travel(resultSet.getInt("travel_id"),user,"test_name","test_desc",new Date(), new Date(),false);
            Collaborator collaborator = new Collaborator(resultSet.getInt("collaborator_id"), travel,user, resultSet.getString("collaborator_name"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), collaborator, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
          }
        }
      }
    }
    return messages;
  }

  @Override
  public List<Message> findMessagesByTravelId(int travel_id) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM message m INNER JOIN collaborator c ON m.collaborator_id=c.collaborator_id WHERE m.travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), "Lau", "lau@SE.com", "1234");
            Travel travel = new Travel(resultSet.getInt("travel_id"),user,"test_name","test_desc",new Date(), new Date(),false);
            Collaborator collaborator = new Collaborator(resultSet.getInt("collaborator_id"), travel, user, resultSet.getString("collaborator_name"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), collaborator, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
          }
        }
      }
    }
    return messages;
  }

  @Override
  public List<Message> findMessagesByUserIdAndTravelId(int user_id, int travel_id) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM message m INNER JOIN collaborator c ON m.collaborator_id=c.collaborator_id WHERE m.collaborator_id =? AND m.travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.setInt(2,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), "Lau", "lau@SE.com", "1234");
            Travel travel = new Travel(resultSet.getInt("travel_id"),user,"test_name","test_desc",new Date(), new Date(),false);
            Collaborator collaborator = new Collaborator(resultSet.getInt("collaborator_id"), travel, user, resultSet.getString("collaborator_name"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), collaborator, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
          }
        }
      }
    }
    return messages;
  }
}
