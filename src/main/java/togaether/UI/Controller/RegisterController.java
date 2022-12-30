package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import togaether.BL.Facade.DBNotFoundException;
import togaether.BL.Facade.UserBadPasswordException;
import togaether.BL.Facade.UserBadConfirmPasswordException;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Facade.UserAlreadyExistException;
import togaether.BL.Facade.UserPseudoAlreadyExistException;

import java.io.IOException;

/**
 * Controller de l'interface graphique Register
 */
public class RegisterController {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField pseudo;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private TextField country;
    @FXML
    private Button register;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    /**
     * Action effectuée lors d'une tentative de Register
     */
    public void onRegisterButtonClick() {
        try {
            UserFacade.getInstance().register(name.getText(), surname.getText(), pseudo.getText(), email.getText(), password.getText(), confirmPassword.getText(), country.getText());
            System.out.println("Inscription réussie");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
        } catch (UserBadConfirmPasswordException e) {
            System.out.println("Mots de passe différents");
        } catch (UserAlreadyExistException e) {
            System.out.println("Mail déjà utilisé");
        } catch (UserPseudoAlreadyExistException e) {
            System.out.println("Pseudo déjà utilisé");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
        }

    }

    public void switchToSceneLoginFrame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
