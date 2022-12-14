package togaether.BL.Facade;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouver dans la base de données
 */
public class UserNotFoundException extends Exception {

  UserNotFoundException() {
    super();
  }
  UserNotFoundException(String e) {
    super(e);
  }
}
