package togaether.DB.Postgres;

import togaether.BL.Model.*;
import togaether.DB.TrophyDAO;
import togaether.DB.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrophyDAOPostgres implements TrophyDAO {

  PostgresFactory postgres;

  public TrophyDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public void createTrophy(String trophy_name, int trophy_categorie, int trophy_value, String trophy_image) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO trophy(trophy_name, trophy_category, trophy_value, trophy_image) VALUES(?,?,?,?);";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setString(1, trophy_name);
        statement.setInt(2, trophy_categorie);
        statement.setInt(3, trophy_value);
        statement.setString(4, trophy_image);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public void updateTrophy(Trophy trophy) throws SQLException {
    try(Connection connection = this.postgres.getConnection()) {
      String query = "UPDATE trophy SET trophy_name = ? , trophy_category = ? , trophy_value = ? , trophy_image = ? WHERE trophy_id = ? ;";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(5, trophy.getId());
        statement.setString(1, trophy.getName());
        statement.setInt(2, trophy.getCategory_id());
        statement.setInt(3, trophy.getValue());
        statement.setString(4, trophy.getImage());

        statement.executeUpdate();
      }
    }
  }

  @Override
  public void deleteTrophyById(int trophy_id) throws SQLException {
    try(Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM trophy WHERE trophy_id=?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,trophy_id);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public void createTrophyUser(int trophy_id, int user_id) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO user_trophy(trophy_id, user_id) VALUES(?,?);";
      try(PreparedStatement statement =
                  connection.prepareStatement(query);){
        statement.setInt(1, trophy_id);
        statement.setInt(2, user_id);
        statement.executeUpdate();
      }
    }
  }

  @Override
  public List<Trophy> findAllTrophies() throws SQLException {
    ArrayList<Trophy> trophies = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy t INNER JOIN trophy_category tc ON t.trophy_category=tc.trophy_category_id;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            trophies.add(new Trophy(resultSet.getInt("trophy_id"), resultSet.getString("trophy_name"), resultSet.getString("trophy_category_name"), resultSet.getInt("trophy_category_id"), resultSet.getInt("trophy_value"), resultSet.getString("trophy_image")));
          }
        }
      }
    }
    return trophies;
  }
  @Override
  public List<Trophy> findTrophiesByCategories(String category) throws SQLException {
    ArrayList<Trophy> trophies = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy t INNER JOIN trophy_category tc ON t.trophy_category=tc.trophy_category_id WHERE tc.trophy_category_name =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1, category);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            trophies.add(new Trophy(resultSet.getInt("trophy_id"), resultSet.getString("trophy_name"), resultSet.getString("trophy_category_name"), resultSet.getInt("trophy_category_id"), resultSet.getInt("trophy_value"), resultSet.getString("trophy_image")));
          }
        }
      }
    }
    return trophies;
  }

  @Override
  public List<Trophy> findNotOwnedTrophiesByCategories(String category, int user_id) throws SQLException {
    ArrayList<Trophy> trophies = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy t INNER JOIN trophy_category tc ON t.trophy_category=tc.trophy_category_id WHERE tc.trophy_category_name =? AND t.trophy_id NOT IN (SELECT ut.trophy_id FROM user_trophy ut WHERE ut.user_id =?);";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setString(1, category);
        statement.setInt(2, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            trophies.add(new Trophy(resultSet.getInt("trophy_id"), resultSet.getString("trophy_name"), resultSet.getString("trophy_category_name"), resultSet.getInt("trophy_category_id"), resultSet.getInt("trophy_value"), resultSet.getString("trophy_image")));
          }
        }
      }
    }
    return trophies;
  }

  @Override
  public List<CategoryTrophy> findAllCategories() throws SQLException {
    ArrayList<CategoryTrophy> categories = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy_category;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            categories.add(new CategoryTrophy(resultSet.getInt("trophy_category_id"), resultSet.getString("trophy_category_name")));
          }
        }
      }
    }
    return categories;
  }

  @Override
  public Trophy findTrophyById(int trophy_id) throws SQLException {
    Trophy trophy = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy t INNER JOIN trophy_category tc ON t.trophy_category=tc.trophy_category_id WHERE t.trophy_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, trophy_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            trophy = new Trophy(resultSet.getInt("trophy_id"), resultSet.getString("trophy_name"), resultSet.getString("trophy_category_name"), resultSet.getInt("trophy_category_id"), resultSet.getInt("trophy_value"), resultSet.getString("trophy_image"));
          }
        }
      }
    }
    return trophy;
  }

  @Override
  public List<Trophy> findTrophiesByUserId(int user_id) throws SQLException {
    ArrayList<Trophy> trophies = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM trophy t INNER JOIN trophy_category tc ON t.trophy_category=tc.trophy_category_id INNER JOIN user_trophy ut ON t.trophy_id=ut.trophy_id WHERE ut.user_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            trophies.add(new Trophy(resultSet.getInt("trophy_id"), resultSet.getString("trophy_name"), resultSet.getString("trophy_category_name"), resultSet.getInt("trophy_category_id"), resultSet.getInt("trophy_value"), resultSet.getString("trophy_image")));
          }
        }
      }
    }
    return trophies;
  }
  @Override
  public int countAllOwnedTravelsByUserId(int user_id) throws SQLException {
    int count = 0;

    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT COUNT(*) FROM travel t WHERE t.owner =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            count = resultSet.getInt(1);}
        }
      }
    }

    return count;
  }
  @Override
  public int countAllParticipatingTravelsByUserId(int user_id) throws SQLException {
    int count = 0;

    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT COUNT(*) FROM collaborator WHERE user_id =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            count = resultSet.getInt(1);}
        }
      }
    }

    return count;
  }
  @Override
  public int countMaxSumExpensesByUserId(int user_id) throws SQLException {
    int count = 0;

   /* try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT MAX(SUM(e.value)) FROM collaborator c INNER JOIN expense e ON c.collaborator_id=e.collaborator_id WHERE user_id =? GROUP BY e.value, e.travel_id";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            count = resultSet.getInt(1);}
        }
      }
    }*/

    return count;
  }
  @Override
  public int countMessageByUserId(int user_id) throws SQLException {
    int count = 0;

    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT COUNT(*) FROM message m INNER JOIN collaborator c ON m.collaborator_id=c.collaborator_id WHERE c.user_id =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            count = resultSet.getInt(1);}
        }
      }
    }

    return count;
  }
  @Override
  public int countFriendsByUserId(int user_id) throws SQLException {
    int count = 0;

    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT COUNT(*) FROM friend WHERE c.user_id1 =? OR c.user_id2 =?";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        statement.setInt(2, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            count = resultSet.getInt(1);}
        }
      }
    }

    return count;
  }
}
