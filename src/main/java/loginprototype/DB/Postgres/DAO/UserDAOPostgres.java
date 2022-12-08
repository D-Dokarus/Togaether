package loginprototype.DB.Postgres.DAO;

import loginprototype.DB.DAO.UserDAO;
import loginprototype.BL.Model.User;
import loginprototype.DB.Postgres.PostgresFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOPostgres implements UserDAO {

  PostgresFactory postgres;

  public UserDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public void insertUser(String user_name, String user_email, String password)  throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO users(user_name, user_email, password) VALUES(?,?, crypt(?,gen_salt('bf', 8)))";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setString(1,user_name);
        statement.setString(2,user_email);
        statement.setString(3,password);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public ResultSet findByName(String user_name) throws SQLException{
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM users WHERE user_name =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_name);
        try (ResultSet resultSet = statement.executeQuery()) {
          System.out.println("heho");
          System.out.println(resultSet);
          return resultSet;
        }
      }
    }
  }

  @Override
  public List<User> findByEmail(String user_email) throws SQLException{
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM users WHERE user_email =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_email);
        try (ResultSet resultSet = statement.executeQuery()) {
          ArrayList<User> users = new ArrayList<>();
          while(resultSet.next())
            users.add(new User(resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("password")));
          return users;
        }
      }
    }
  }

  @Override
  public void deleteUserByUser(int user_id)  throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM users WHERE user_id=?;";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public ResultSet getAll() throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM users";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        try (ResultSet resultSet = statement.executeQuery()) {
          return resultSet;
        }
      }
    }
  }
}
