package togaether.BL.TogaetherException;

/**
 * Exception levée lorsqu'un pseudo d'utilisateur est déjà dans la base de données
 */
public class UserBadEmailException extends Exception {
    public UserBadEmailException() {
        super();
    }
    public UserBadEmailException(String e) {
        super(e);
    }
}
