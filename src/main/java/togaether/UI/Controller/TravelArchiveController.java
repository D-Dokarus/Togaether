package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Travel;
import togaether.UI.SceneController;

import java.util.ArrayList;

public class TravelArchiveController {
    @FXML
    private Label nameTravel;
    @FXML
    private Text descriptionTravel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmedButton;

    @FXML
    private Label labelError;

    @FXML
    protected void initialize() {
        // Récupérer travel connected
        TravelFacade travelFacade = TravelFacade.getInstance();

        this.nameTravel.setText(travelFacade.getTravel().getNameTravel());
        this.descriptionTravel.setText(travelFacade.getTravel().getDescriptionTravel());
    }

    public void onCancelButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        TravelFacade travelFacade = TravelFacade.getInstance();
        try {
            travelFacade.archiveTravel(travelFacade.getTravel());
            SceneController.getInstance().switchToHomePage(event);
            System.out.println("Voyage archivé !");
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            throw new RuntimeException(e);
        }
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onNotificationsButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToNotificationCenter(event);
    }
}
