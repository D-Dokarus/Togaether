package togaether.BL.TogaetherException;

/**
 * Exception levée lorsque le programme n'arrive pas à trouver le document
 */
public class FileNotFoundException extends Exception {
  public FileNotFoundException() {
    super();
  }
  public FileNotFoundException(String e) {
    super(e);
  }
}
