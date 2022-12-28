package togaether.DB.Postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe donnant l'accès à la base de données
 */
public class ConnectionToDB {
  static String url = "jdbc:postgresql://postgresql-togaether-db.alwaysdata.net/togaether-db_db";
  static String user = "togaether-db";
  static String password = "postgres";

  protected Connection getConnection() throws SQLException {
    return DriverManager.getConnection(ConnectionToDB.url, ConnectionToDB.user, ConnectionToDB.password);
  }
}
