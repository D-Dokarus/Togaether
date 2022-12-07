package loginprototype.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresFactory {

  static String url = "jdbc:postgresql://localhost:5432/toaegether_db";
  static String user = "postgres";
  static String password = "postgres";

  public UserDAO getUserDAO() {
    return new UserDAOPostgres(this);
  }

  protected Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DAO.url, DAO.user, DAO.password);
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
