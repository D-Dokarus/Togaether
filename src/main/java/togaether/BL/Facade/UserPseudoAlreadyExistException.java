package togaether.BL.Facade;

/**
 * Exception levée lorsqu'un pseudo d'utilisateur est déjà dans la base de données
 */
public class UserPseudoAlreadyExistException extends Exception {

  UserPseudoAlreadyExistException() {
    super();
  }
  UserPseudoAlreadyExistException(String e) {
    super(e);
  }
}
