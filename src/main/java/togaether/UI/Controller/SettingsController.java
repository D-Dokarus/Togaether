package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.User;
import togaether.BL.TogaetherException.*;
import togaether.UI.SceneController;

import java.sql.SQLException;
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
    @FXML
    private Label labelError;

    User user;

    @FXML
    protected void initialize() {
        // Récupérer activity connected
        UserFacade userFacade = UserFacade.getInstance();
        try {
            this.user = userFacade.getConnectedUser();
        } catch (Exception e) {
            System.out.println("Attention : Aucun user connecté, veuillez réessayer");
            //this.labelError.setText("Attention : L'activité n'a pas pu être trouvée, veuillez réessayer");
            throw new RuntimeException(e);
        }
        // Remplir les champs avec les informations de l'activité
        this.newname.setText(this.user.getName());
        this.newsurname.setText(this.user.getSurname());
        this.newemail.setText(this.user.getEmail());
        this.newpseudo.setText(this.user.getPseudo());
        this.newcountry.setText(this.user.getCountry());


    }
    public void onSaveChangeButtonClick() {

        try {
            UserFacade.getInstance().updateAccount(newname.getText(), newsurname.getText(), newpseudo.getText(), newemail.getText(), newpassword.getText(), newconfirmPassword.getText(), newcountry.getText(), this.user.getId());

            System.out.println("Inscription réussie");
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void switchToDeleteAccountFrame(ActionEvent event) {
        SceneController.getInstance().switchToDeleteAccount(event);
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onLogoutButtonClicked(ActionEvent event) {
        UserFacade.getInstance().logout();
        SceneController.getInstance().switchToLogin(event);
    }

}
