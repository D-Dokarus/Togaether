package togaether.BL.Facade;

/**
 * Exception levée lorsqu'un utilisateur est déjà dans la base de données
 */
public class UserAlreadyExistException extends Exception {

  UserAlreadyExistException() {
    super();
  }
  UserAlreadyExistException(String e) {
    super(e);
  }
}
