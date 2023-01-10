package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import togaether.BL.TogaetherException.*;
import togaether.BL.Facade.UserFacade;
import togaether.UI.SceneController;

import java.io.File;
import java.net.MalformedURLException;

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
    @FXML
    private Label labelError;
    @FXML
    private ImageView logo;

    public RegisterController() {
    }

    public void initialize() throws MalformedURLException {
        String sep = File.separator;
        String path = "";
        //set logo image with image/logo.png
        File image = new File(System.getProperty("user.dir") + sep  + "image" + sep + "logo.png");
        if (image.exists()) {
            this.logo.setImage(new Image(image.toURI().toURL().toExternalForm(), 200, 200, true, true));
        } else {
            System.out.println("Image pas trouvée");
        }

    }

    /**
     * Action effectuée lors d'une tentative d'inscription
     */
    public void onRegisterButtonClick(ActionEvent event) {
        try {
            UserFacade.getInstance().register(name.getText(), surname.getText(), pseudo.getText(), email.getText(), password.getText(), confirmPassword.getText(), country.getText());
            SceneController.getInstance().switchToLogin(event);
            System.out.println("Informations changées");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
            this.labelError.setText("Mauvais mot de passe");
        } catch (UserBadConfirmPasswordException e) {
            System.out.println("Mots de passe différents");
            this.labelError.setText("Mots de passe différents");
        } catch (UserAlreadyExistException e) {
            System.out.println("Mail déjà utilisé");
            this.labelError.setText("Mail déjà utilisé");
        } catch (UserPseudoAlreadyExistException e) {
            System.out.println("Pseudo déjà utilisé");
            this.labelError.setText("Pseudo déjà utilisé");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
            this.labelError.setText("Erreur lors de la connexion à la DB");
        }catch (UserBadEmailException e) {
            System.out.println("Email invalide");
            this.labelError.setText("Email invalide");
        }

    }

    public void switchToSceneLoginFrame(ActionEvent event) {
        SceneController.getInstance().switchToLogin(event);
    }
}
