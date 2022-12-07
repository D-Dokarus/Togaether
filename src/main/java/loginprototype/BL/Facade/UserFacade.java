package loginprototype.BL.Facade;

import loginprototype.DB.AbstractFactory;
import loginprototype.DB.Model.User;
import loginprototype.DB.DAO.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class UserFacade {

  static private User user = null;

  static private UserFacade instance = new UserFacade();

  static public void login(String email, String password) throws UserNotFoundException, UserBadPasswordException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    try {
      List<User> u = userDB.findByEmail(email);
      if(u.size() > 0) {
        if (BCrypt.checkpw(password, u.get(0).getPassword())) {
          UserFacade.user = u.get(0);
        }
        else
          throw new UserBadPasswordException();
      }
      else
        throw new UserNotFoundException();

    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }

  public static UserFacade createInstance() {
    return instance;
  }
}
