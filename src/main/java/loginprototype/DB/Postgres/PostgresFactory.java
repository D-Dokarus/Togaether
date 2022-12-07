package loginprototype.DB.Postgres;

import loginprototype.DB.AbstractFactory;
import loginprototype.DB.DAO.UserDAO;
import loginprototype.DB.Postgres.DAO.UserDAOPostgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresFactory extends AbstractFactory {

  ConnectionToBD connection;

  public PostgresFactory() {
    this.connection = new ConnectionToBD();
  }
  public UserDAO getUserDAO() {
    return new UserDAOPostgres(this);
  }

   public Connection getConnection() throws SQLException {
    return this.connection.getConnection();
  }

  public static void main(String... args) {
    //FAIRE DES TESTS
  }
}
