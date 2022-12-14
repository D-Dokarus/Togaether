package togaether.UI.Controller;

import togaether.BL.Facade.DBNotFoundException;
import togaether.BL.Facade.UserBadPasswordException;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Facade.UserNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller de l'interface graphique Login
 */
public class LoginController {
  @FXML
  private TextField email;
  @FXML
  private PasswordField password;
  @FXML
  private Button seConnecter;

  /**
   * Action effectuée lors d'une tentative de login
   */
  public void onLoginButtonClick() {
    try {
      UserFacade.createInstance().login(email.getText(), password.getText());
      System.out.println("Login réussi");
    }
    catch (UserNotFoundException e) {
      System.out.println("Cet utilisateur n'existe pas");
    }
    catch (UserBadPasswordException e) {
      System.out.println("Mauvais mot de passe");
    }
    catch (DBNotFoundException e) {
      System.out.println("Erreur lors de la connexion à la DB");
    }

  }
}
