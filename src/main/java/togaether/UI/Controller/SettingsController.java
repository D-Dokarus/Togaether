package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import togaether.BL.Facade.*;
import togaether.UI.SceneController;

import java.io.IOException;

/**
 * Controller de l'interface graphique Login
 */
public class SettingsController {
    @FXML
    private TextField newname;
    @FXML
    private TextField newsurname;
    @FXML
    private TextField newemail;
    @FXML
    private TextField newpseudo;
    @FXML
    private PasswordField newpassword;
    @FXML
    private PasswordField newconfirmPassword;
    @FXML
    private TextField newcountry;
    @FXML
    private Button returnButton;
    @FXML
    private Button saveChangeButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    /**
     * Action effectuée lors d'une tentative de login
     */
    public void onSaveChangeButtonClick() {
        try {
            //Update if not empty
            if(!newname.getText().isEmpty()){
                UserFacade.createInstance().updateName(newname.getText());
            }
            if(!newsurname.getText().isEmpty()){
                UserFacade.createInstance().updateSurname(newsurname.getText());
            }
            if(!newemail.getText().isEmpty()){
                UserFacade.createInstance().updateEmail(newemail.getText());
            }
            if(!newpseudo.getText().isEmpty()){
                UserFacade.createInstance().updatePseudo(newpseudo.getText());
            }
            if(!newpassword.getText().isEmpty()){
                UserFacade.createInstance().updatePassword(newpassword.getText(), newconfirmPassword.getText());
            }
            if(!newcountry.getText().isEmpty()){
                UserFacade.createInstance().updateCountry(newcountry.getText());
            }
            System.out.println("Update réussi");
        } catch (UserBadPasswordException e) {
            System.out.println("Mauvais mot de passe");
        } catch (DBNotFoundException e) {
            System.out.println("Erreur lors de la connexion à la DB");
        } catch (UserAlreadyExistException e) {
            System.out.println("Cet utilisateur existe déjà");
        } catch (UserBadConfirmPasswordException e) {
            System.out.println("Mauvais mot de passe de confirmation");
        } catch (UserPseudoAlreadyExistException e) {
            System.out.println("Ce pseudo existe déjà");
        }

    }
    public void test(){
        System.out.println(newemail.getText().isBlank());
        System.out.println(newpassword.getText().isEmpty());
    }

    public void switchToDeleteAccountFrame(ActionEvent event) {
        SceneController.getInstance().switchToDeleteAccount(event);
    }
}
