package togaether.BL.TogaetherException;

/**
 * Exception levée lorsque l'utilisateur a donné un mauvais mot de passe
 */
public class UserBadPasswordException extends Exception {
  public UserBadPasswordException() {
    super();
  }
  public UserBadPasswordException(String e) {
    super(e);
  }
}
