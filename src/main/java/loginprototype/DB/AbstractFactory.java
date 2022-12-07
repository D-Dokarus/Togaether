package loginprototype.DB;

import loginprototype.DB.DAO.UserDAO;
import loginprototype.DB.Postgres.PostgresFactory;

public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  static final public UserDAO getUserDAO() {
    return instance.getUserDAO();
  }

  static void createInstance() {

  }
}
