package togaether.BL.Facade;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouver dans la base de données
 */
public class CollaboratorNotFoundException extends Exception {

  CollaboratorNotFoundException() {
    super();
  }
  CollaboratorNotFoundException(String e) {
    super(e);
  }
}