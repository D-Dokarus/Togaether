package loginprototype.BL.Facade;

public class DBNotFoundException extends Exception {
  DBNotFoundException() {
    super();
  }
  DBNotFoundException(String e) {
    super(e);
  }
}
