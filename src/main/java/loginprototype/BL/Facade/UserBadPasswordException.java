package loginprototype.BL.Facade;

public class UserBadPasswordException extends Exception {
  UserBadPasswordException() {
    super();
  }
  UserBadPasswordException(String e) {
    super(e);
  }
}
