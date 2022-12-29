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

  /* Old Version
  public void insertUser(String user_name, String user_email, String user_password)  throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO public.user(user_name, user_email, user_password) VALUES(?,?, crypt(?,gen_salt('bf', 8)))";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setString(1,user_name);
        statement.setString(2,user_email);
        statement.setString(3,user_password);
        statement.executeUpdate();
      }
    }
  }*/
  @Override
  public void insertUser(String user_name, String user_surname, String user_pseudo, String user_country, String user_dateCreation, String user_email, String user_password) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO public.user(user_name, user_surname, user_pseudo, user_country, user_email, user_password) VALUES(?,?,?,?,?,?) ;";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setString(1,user_name);
        statement.setString(2,user_surname);
        statement.setString(3,user_pseudo);
        statement.setString(4,user_country);
        statement.setString(5,user_email);
        statement.setString(6,user_password);
        statement.executeUpdate();
      }
    }
  }
  @Override
  public void insertUser(User user) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO public.user(user_name, user_email, user_password, user_surname, user_pseudo, user_country) VALUES(?,?,?,?,?,?); ";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setString(1,user.getName());
        statement.setString(2,user.getEmail());
        statement.setString(3,user.getPassword());
        statement.setString(4,user.getSurname());
        statement.setString(5,user.getPseudo());
        statement.setString(6,user.getCountry());
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
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password")));
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
  public User findByPseudo(String user_pseudo) throws SQLException{
    User u = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user WHERE user_pseudo =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_pseudo);
        try (ResultSet resultSet = statement.executeQuery()) {
          while(resultSet.next())
            u = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
        }
      }
    }
    return u;
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
            u = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
        }
      }
    }
    return u;
  }

  @Override
  public User findByEmail(String user_email) throws SQLException{
    User u = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM public.user WHERE user_email =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1,user_email);
        try (ResultSet resultSet = statement.executeQuery()) {
          ArrayList<User> users = new ArrayList<>();
          while(resultSet.next())
            u = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password"));
        }
      }
    }
    return u;
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
            users.add(new User(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("user_email"), resultSet.getString("user_password")));
        return users;
        }
      }
    }
  }
}
