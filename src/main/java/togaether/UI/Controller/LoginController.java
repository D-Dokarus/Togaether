package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import togaether.BL.Facade.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.BL.TogaetherException.UserBadPasswordException;
import togaether.BL.TogaetherException.UserNotFoundException;
import togaether.UI.SceneController;

import java.io.File;
import java.net.MalformedURLException;

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
    @FXML
    private Button register;
    @FXML
    private Button forgotPassword;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Label labelError;
    @FXML
    private ImageView logo;


    public void initialize() throws MalformedURLException {
        String sep = File.separator;
        String path = "";
        //set logo image with image/logo.png
        File image = new File(System.getProperty("user.dir") + sep  + "image" + sep + "logo.png");
        if (image.exists()) {
            this.logo.setImage(new Image(image.toURI().toURL().toExternalForm(), 150, 150, true, true));
        } else {
            System.out.println("Image pas trouvée");
        }

    }

    /**
     * Action effectuée lors d'une tentative de login
     */
    public void onLoginButtonClick(ActionEvent event) {
        try {
            UserFacade.getInstance().login(email.getText(), password.getText());
            SceneController.getInstance().switchToHomePage(event);
            System.out.println("Login réussi");
        } catch (UserNotFoundException e) {
            System.out.println("Cet utilisateur n'existe pas");
            this.labelError.setText("Cet utilisateur n'existe pas");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
            this.labelError.setText("Mauvais mot de passe");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
            this.labelError.setText("Erreur lors de la connexion à la DB");
        }

    }
    public void switchToSceneRegisterFrame(ActionEvent event) {
        SceneController.getInstance().switchToRegister(event);
    }
}
