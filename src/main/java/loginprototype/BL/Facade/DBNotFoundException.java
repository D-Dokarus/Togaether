package loginprototype.BL.Facade;

/**
 * Exception levée lorsque le programme n'arrive pas à se connecter à la base de données
 */
public class DBNotFoundException extends Exception {
  DBNotFoundException() {
    super();
  }
  DBNotFoundException(String e) {
    super(e);
  }
}
