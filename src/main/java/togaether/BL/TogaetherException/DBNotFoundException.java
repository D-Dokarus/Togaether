package togaether.BL.TogaetherException;

/**
 * Exception levée lorsque le programme n'arrive pas à se connecter à la base de données
 */
public class DBNotFoundException extends Exception {
  public DBNotFoundException() {
    super();
  }
  public DBNotFoundException(String e) {
    super(e);
  }
}
