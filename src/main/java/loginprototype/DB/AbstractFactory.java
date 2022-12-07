package loginprototype.DB;

import loginprototype.DB.DAO.UserDAO;
import loginprototype.DB.Postgres.PostgresFactory;

public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  abstract public UserDAO getUserDAO();

  public static AbstractFactory createInstance() {
    return instance;
  }
}
