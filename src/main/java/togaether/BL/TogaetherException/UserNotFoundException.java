package togaether.BL.TogaetherException;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouver dans la base de données
 */
public class UserNotFoundException extends Exception {
  public UserNotFoundException() {
    super();
  }
  public UserNotFoundException(String e) {
    super(e);
  }
}
