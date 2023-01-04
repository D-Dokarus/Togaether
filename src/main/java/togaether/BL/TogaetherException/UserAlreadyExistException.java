package togaether.BL.TogaetherException;

/**
 * Exception levée lorsqu'un utilisateur est déjà dans la base de données
 */
public class UserAlreadyExistException extends Exception {

  public UserAlreadyExistException() {
    super();
  }
  public UserAlreadyExistException(String e) {
    super(e);
  }
}
