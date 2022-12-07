package loginprototype.BL.Facade;

import loginprototype.DB.AbstractFactory;
import loginprototype.DB.Model.User;
import loginprototype.DB.DAO.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class UserFacade {

  static private User user = null;

  static final public String LOGIN_SUCCESS = "LOGIN_SUCCESS";
  static final public String BAD_EMAIL = "BAD_EMAIL";
  static final public String BAD_PASSWORD = "BAD_PASSWORD";

  static public String login(String email, String password) {
    UserDAO userDB = AbstractFactory.getUserDAO();
    try {
      List<User> u = userDB.findByEmail(email);
      if(u.size() > 0) {
        if (BCrypt.checkpw(password, u.get(0).getPassword())) {
          UserFacade.user = u.get(0);
          return UserFacade.LOGIN_SUCCESS;
        }
        else
          return UserFacade.BAD_PASSWORD;
      }
      else
        return UserFacade.BAD_EMAIL;

    } catch (SQLException e) {
      System.out.println(e);
    }
    return "ERROR";
  }
}
