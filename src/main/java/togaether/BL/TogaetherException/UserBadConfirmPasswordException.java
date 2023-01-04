package togaether.BL.TogaetherException;

/**
 * Exception levée lorsque l'utilisateur a donné un mauvais mot de passe
 */
public class UserBadConfirmPasswordException extends Exception {
  public UserBadConfirmPasswordException() {
    super();
  }
  public UserBadConfirmPasswordException(String e) {
    super(e);
  }
}
