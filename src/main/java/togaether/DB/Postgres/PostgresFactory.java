package togaether.DB.Postgres;

import togaether.DB.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe héritant d'AbstractFactory, produisant des DAO fonctionnant sur une base de données Postgres
 */
public class PostgresFactory extends AbstractFactory {

  ConnectionToDB connection;

  public PostgresFactory() {
    this.connection = new ConnectionToDB();
  }
  public UserDAO getUserDAO() {
    return new UserDAOPostgres(this);
  }
  public MessageDAO getMessageDAO() {return new MessageDAOPostgres(this);}

   public Connection getConnection() throws SQLException {
    return this.connection.getConnection();
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
