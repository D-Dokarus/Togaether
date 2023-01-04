package togaether.BL.TogaetherException;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouver dans la base de données
 */
public class CollaboratorNotFoundException extends Exception {

  public CollaboratorNotFoundException() {
    super();
  }
  public CollaboratorNotFoundException(String e) {
    super(e);
  }
}