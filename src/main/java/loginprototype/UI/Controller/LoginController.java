package loginprototype.UI.Controller;

import loginprototype.BL.Facade.DBNotFoundException;
import loginprototype.BL.Facade.UserBadPasswordException;
import loginprototype.BL.Facade.UserFacade;
import loginprototype.BL.Facade.UserNotFoundException;
import loginprototype.DB.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import loginprototype.DB.DAO.UserDAO;

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
