package togaether.BL.Facade;

import togaether.DB.AbstractFactory;
import togaether.BL.Model.User;
import togaether.DB.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe singleton regroupant différentes foncionnalité lié à l'utilisateur courant
 */
public class UserFacade {

  static private User connectedUser = null;

  static private UserFacade instance = new UserFacade();

  /**
   * Méthode vérifiant les identifiants données par l'utilisateur. Si les identifiants ne correspondent pas, ou si une erreur se produit, une exception est levée
   * @param email
   * @param password
   * @throws UserNotFoundException
   * @throws UserBadPasswordException
   * @throws DBNotFoundException
   */
  public void login(String email, String password) throws UserNotFoundException, UserBadPasswordException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    try {
      User u = userDB.findByEmail(email);
      if(u != null) {
        if (BCrypt.checkpw(password, u.getPassword())) {
          UserFacade.connectedUser = u;
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

  public static User getConnectedUser(){ return connectedUser; }
}
