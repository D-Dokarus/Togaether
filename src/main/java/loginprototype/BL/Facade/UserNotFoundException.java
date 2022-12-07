package loginprototype.BL.Facade;

public class UserNotFoundException extends Exception {

  UserNotFoundException() {
    super();
  }
  UserNotFoundException(String e) {
    super(e);
  }
}
