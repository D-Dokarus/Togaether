package togaether.BL.Facade;

/**
 * Exception levée lorsque l'utilisateur a donné un mauvais mot de passe
 */
public class UserBadConfirmPasswordException extends Exception {
  UserBadConfirmPasswordException() {
    super();
  }
  UserBadConfirmPasswordException(String e) {
    super(e);
  }
}
