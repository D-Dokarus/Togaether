package togaether.BL.Facade;

import togaether.DB.AbstractFactory;
import togaether.BL.Model.User;
import togaether.DB.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.time.LocalDateTime;

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
    System.out.println("aa");

    try {
      System.out.println("bb");
      User u = userDB.findByEmail(email);
        System.out.println(u);
      if(u != null) {
        System.out.println("cc");
        if (BCrypt.checkpw(password, u.getPassword())) {
            System.out.println("dd");
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

  public void register(String name, String surname, String pseudo, String email, String password, String confirmPassword, String country) throws UserBadPasswordException, UserBadConfirmPasswordException, UserAlreadyExistException, UserPseudoAlreadyExistException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    if(!password.equals(confirmPassword))
      throw new UserBadConfirmPasswordException();

    if(password.length() < 8)
      throw new UserBadPasswordException();

    try {
      if(userDB.findByEmail(email) != null)
        throw new UserAlreadyExistException();

      if(userDB.findByPseudo(pseudo) != null)
        throw new UserPseudoAlreadyExistException();

      User u = new User(name, surname, pseudo, email, BCrypt.hashpw(password, BCrypt.gensalt()), country);
      userDB.insertUser(u);
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }



  public static UserFacade createInstance() {
    return instance;
  }

  public static User getConnectedUser(){ return connectedUser; }
}
