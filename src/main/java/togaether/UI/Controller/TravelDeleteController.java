package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.UI.SceneController;

public class TravelDeleteController {
    @FXML
    private Label nameTravel;
    @FXML
    private Text descriptionTravel;

    @FXML
    protected void initialize() {
        // Récupérer travel connected
        TravelFacade travelFacade = TravelFacade.getInstance();

        this.nameTravel.setText(travelFacade.getTravel().getNameTravel());
        this.descriptionTravel.setText(travelFacade.getTravel().getDescriptionTravel());
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        TravelFacade travelFacade = TravelFacade.getInstance();
        try {
            CollaboratorFacade.getInstance().deleteAllColaboratorByTravel(travelFacade.getTravel());
            travelFacade.deleteTravel(travelFacade.getTravel());
            SceneController.getInstance().switchToHomePage(event);
            System.out.println("Voyage supprimé !");
        } catch (Exception e) {
            System.out.println("Attention : Le voyage n'a pas pu être supprimé, veuillez réessayer");
            throw new RuntimeException(e);
        }
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

}
