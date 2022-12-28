package togaether.DB.Postgres;

import togaether.DB.*;
import java.sql.*;

/**
 * Classe héritant d'AbstractFactory, produisant des DAO fonctionnant sur une base de données Postgres
 */
public class PostgresFactory extends AbstractFactory {

  ConnectionToDB connection;

  public PostgresFactory() {
    this.connection = new ConnectionToDB();
  }
  @Override
  public UserDAO getUserDAO() {
    return new UserDAOPostgres(this);
  }
  @Override
  public MessageDAO getMessageDAO() {return new MessageDAOPostgres(this);}
  @Override
  public NotificationDAO getNotificationDAO(){ return new NotificationDAOPostgres(this);}
  @Override
  public TravelDAO getTravelDAO(){ return new TravelDAOPostgres(this);}

   public Connection getConnection() throws SQLException {
    return this.connection.getConnection();
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
