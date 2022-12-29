package togaether.DB.Postgres;

import togaether.BL.Model.Message;
import togaether.BL.Model.User;
import togaether.DB.MessageDAO;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MessageDAOPostgres implements MessageDAO {


  PostgresFactory postgres;

  public MessageDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public void insertMessage(int travel_id, int user_id, String message_content) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO message(travel_id, user_id, message_content) VALUES(?,?,?);";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setInt(1,travel_id);
        statement.setInt(2,user_id);
        statement.setString(3, message_content);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public List<Message> findMessagesByUserId(int id) throws SQLException {
    ArrayList<Message> messages = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM message m INNER JOIN public.user u ON m.user_id=u.user_id WHERE m.user_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), user, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
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
      String query = "SELECT * FROM message m INNER JOIN public.user u ON m.user_id=u.user_id WHERE travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), user, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
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
      String query = "SELECT * FROM message m INNER JOIN public.user u ON m.user_id=u.user_id WHERE m.user_id =? AND m.travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.setInt(2,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            User user = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
            messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("travel_id"), user, resultSet.getString("message_content"), resultSet.getTimestamp("message_date")));
          }
        }
      }
    }
    return messages;
  }
}
