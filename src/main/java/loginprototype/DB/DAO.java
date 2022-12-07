package loginprototype.DB;

import java.sql.*;

public class DAO {
  static String url = "jdbc:postgresql://localhost:5432/toaegether_db";
  static String user = "postgres";
  static String password = "postgres";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DAO.url, DAO.user, DAO.password);
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
