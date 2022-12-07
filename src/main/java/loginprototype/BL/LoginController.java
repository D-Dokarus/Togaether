package loginprototype.BL;

import loginprototype.DB.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
  @FXML
  private TextField email;
  @FXML
  private PasswordField password;
  @FXML
  private Button seConnecter;

  public void onLoginButtonClick() {
    UserDAO userDB = AbstractFactory.getUserDAO();
    String response = UserFacade.login(email.getText(), password.getText());
    if(response.equals(UserFacade.LOGIN_SUCCESS))
      System.out.println("Login réussi");
    else if (response.equals(UserFacade.BAD_PASSWORD))
      System.out.println("Mauvais mot de passe");
    else if (response.equals(UserFacade.BAD_EMAIL))
      System.out.println("Cet utilisateur n'existe pas");
  }
}
