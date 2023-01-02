import togaether.BL.Model.User;
import togaether.DB.AbstractFactory;
import togaether.DB.UserDAO;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

public class UserFacadeTest {
  @Test
  void isConnectionValid() {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    try {
      User u = userDB.findByEmail("dorian.752@live.fr");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}