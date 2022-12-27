package togaether.DB.Postgres;

import togaether.DB.AbstractFactory;
import togaether.DB.NotificationDAO;
import togaether.DB.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe héritant d'AbstractFactory, produisant des DAO fonctionnant sur une base de données Postgres
 */
public class PostgresFactory extends AbstractFactory {

  ConnectionToBD connection;

  public PostgresFactory() {
    this.connection = new ConnectionToBD();
  }
  public UserDAO getUserDAO() {
    return new UserDAOPostgres(this);
  }
  @Override
  public NotificationDAO getNotificationDAO(){ return new NotificationDAOPostgres(this);}

   public Connection getConnection() throws SQLException {
    return this.connection.getConnection();
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
