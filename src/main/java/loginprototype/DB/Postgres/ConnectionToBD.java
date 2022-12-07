package loginprototype.DB.Postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToBD {
  static String url = "jdbc:postgresql://localhost:5432/toaegether_db";
  static String user = "postgres";
  static String password = "postgres";

  protected Connection getConnection() throws SQLException {
    return DriverManager.getConnection(ConnectionToBD.url, ConnectionToBD.user, ConnectionToBD.password);
  }
}
