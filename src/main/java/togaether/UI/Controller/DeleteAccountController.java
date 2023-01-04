package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.BL.TogaetherException.UserBadPasswordException;
import togaether.BL.Facade.UserFacade;
import togaether.UI.SceneController;

/**
 * Controller de l'interface graphique Login
 */
public class DeleteAccountController {
    @FXML
    private PasswordField password;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    /**
     * Action effectuée lors d'une tentative de login
     */
    public void onDeleteButtonClick(ActionEvent event) {
        try {
            UserFacade.getInstance().deleteAccount(password.getText());
            System.out.println("Compte supprimé");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
        }

    }

    public void switchToSceneRegisterFrame(ActionEvent event) {
        SceneController.getInstance().switchToRegister(event);
    }

}
