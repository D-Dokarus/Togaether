package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Travel;
import togaether.UI.SceneController;

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

    /**
     * The connected Travel
     */
    Travel travel;

    @FXML
    protected void initialize() {
        // Récupérer user connected
        // Récupérer travel connected
        TravelFacade travelFacade = TravelFacade.getInstance();
        try {
            this.travel = travelFacade.findTravelById(4);
            // Fermer la page pour revenir à la page principale
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être trouvé, veuillez réessayer");
            throw new RuntimeException(e);
        }
        this.nameTravel.setText(this.travel.getNameTravel());
        this.descriptionTravel.setText(this.travel.getDescriptionTravel());
    }

    public void onCancelButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        TravelFacade travelFacade = TravelFacade.getInstance();
        try {
            travelFacade.archiveTravel(this.travel);
            SceneController.getInstance().switchToHomePage(event);
            System.out.println("Voyage archivé !");
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            this.labelError.setText("Attention : Le voyage n'a pas pu être archivé, veuillez réessayer");
            throw new RuntimeException(e);
        }
    }

}
