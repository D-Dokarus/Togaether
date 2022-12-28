package togaether.DB.Postgres;

import togaether.DB.UserDAO;
import togaether.BL.Model.User;

import java.sql.*;
import java.util.*;

public class UserDAOPostgres implements UserDAO {

  PostgresFactory postgres;

  public UserDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public void insertUser(String user_name, String user_email, String password)  throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO public.user(user_name, user_email, password) VALUES(?,?, crypt(?,gen_salt('bf', 8)))";
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
  public List<User> findByName(String user_name) throws SQLException{
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user WHERE user_name =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_name);
        try (ResultSet resultSet = statement.executeQuery()) {

          ArrayList<User> users = new ArrayList<>();
          while(resultSet.next())
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("password")));
          return users;
          /*
          System.out.println("heho");
          System.out.println(resultSet);
          return resultSet;
          */
        }
      }
    }
  }

  @Override
  public User findByUserId(int idUser) throws SQLException {
    User u = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user WHERE user_id =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,idUser);
        try (ResultSet resultSet = statement.executeQuery()) {
          while(resultSet.next())
            u = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("password"));
        }
      }
    }
    return u;
  }

  @Override
  public List<User> findByEmail(String user_email) throws SQLException{
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user WHERE user_email =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_email);
        try (ResultSet resultSet = statement.executeQuery()) {
          ArrayList<User> users = new ArrayList<>();
          while(resultSet.next())
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("password")));
          return users;
        }
      }
    }
  }

  @Override
  public void deleteUserByUserId(int user_id)  throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM public.user WHERE user_id=?;";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public List<User> getAll() throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        try (ResultSet resultSet = statement.executeQuery()) {
          ArrayList<User> users = new ArrayList<>();
          while(resultSet.next())
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("password")));
          return users;
        }
      }
    }
  }
}
