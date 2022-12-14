package togaether.BL.Facade;

/**
 * Exception levée lorsque l'utilisateur a donné un mauvais mot de passe
 */
public class UserBadPasswordException extends Exception {
  UserBadPasswordException() {
    super();
  }
  UserBadPasswordException(String e) {
    super(e);
  }
}
