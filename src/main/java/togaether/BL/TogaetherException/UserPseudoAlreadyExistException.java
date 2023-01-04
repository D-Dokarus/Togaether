package togaether.BL.TogaetherException;

/**
 * Exception levée lorsqu'un pseudo d'utilisateur est déjà dans la base de données
 */
public class UserPseudoAlreadyExistException extends Exception {
  public UserPseudoAlreadyExistException() {
    super();
  }
  public UserPseudoAlreadyExistException(String e) {
    super(e);
  }
}
