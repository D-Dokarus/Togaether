package loginprototype.DB;

public abstract class AbstractFactory {
  static private PostgresFactory instance = new PostgresFactory();

  static final public UserDAO getUserDAO() {
    return instance.getUserDAO();
  }

  static void createInstance() {

  }
}
