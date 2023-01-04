package togaether.BL.Facade;

import togaether.BL.Model.User;
import togaether.BL.TogaetherException.*;
import togaether.DB.AbstractFactory;
import togaether.DB.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;

/**
 * Classe singleton regroupant différentes foncionnalité lié à l'utilisateur courant
 */
public class UserFacade {

  private User connectedUser = null;

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
          this.connectedUser = u;
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
  public User getConnectedUser(){ return connectedUser; }

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

  public void deleteAccount(String password) throws UserBadPasswordException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    if(!BCrypt.checkpw(password, connectedUser.getPassword()))
      throw new UserBadPasswordException();

    try {
      userDB.deleteUserByUserId(connectedUser.getId());
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }

  public void updateName(String name) throws DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    UserDAO userDB = fact.getUserDAO();

    try {
      userDB.updateName(name, 6);
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }

    public void updateSurname(String surname) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
        userDB.updateSurname(surname, 6);
        } catch (SQLException e) {
        throw new DBNotFoundException();
        }
    }

    public void updatePseudo(String pseudo) throws DBNotFoundException, UserPseudoAlreadyExistException {
      AbstractFactory fact = AbstractFactory.createInstance();
      UserDAO userDB = fact.getUserDAO();

      try {
        if (userDB.findByPseudo(pseudo) != null)
          throw new UserPseudoAlreadyExistException();

        userDB.updatePseudo(pseudo, 6);
      } catch (SQLException e) {
        throw new DBNotFoundException();
      }

    }

    public void updateEmail(String email) throws DBNotFoundException, UserAlreadyExistException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
          if(userDB.findByEmail(email) != null)
            throw new UserAlreadyExistException();

        userDB.updateEmail(email, 6);
        } catch (SQLException e) {
        throw new DBNotFoundException();
        }
    }

    public void updatePassword(String password, String confirmPassword) throws DBNotFoundException, UserBadConfirmPasswordException, UserBadPasswordException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        if(!password.equals(confirmPassword))
          throw new UserBadConfirmPasswordException();

        if(password.length() < 8)
            throw new UserBadPasswordException();

        try {
        userDB.updatePassword(BCrypt.hashpw(password, BCrypt.gensalt()), connectedUser.getId());
        } catch (SQLException e) {
        throw new DBNotFoundException();
        }
    }

    public void updateCountry(String country) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
        userDB.updateCountry(country, 6);
        } catch (SQLException e) {
        throw new DBNotFoundException();
        }
    }



  public static UserFacade getInstance() {
    return instance;
  }

}
