package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.ItineraryFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.TransportCategory;
import togaether.BL.Model.Travel;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.Postgres.PostgresFactory;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ItineraryCreateController {

    @FXML
    private Button returnButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private DatePicker dateItinerary;
    @FXML
    private DatePicker timeItinerary;
    @FXML
    private TextArea nameTravel;
    @FXML
    private TextArea descriptionTravel;
    @FXML
    private Label labelError;
    @FXML
    private ChoiceBox<String> selectCategory;

    private List<String> nameCatTransport;


    @FXML
    protected void initialize() {
        initializeView();
    }

    public void initializeView(){
        ItineraryFacade itineraryFacade = ItineraryFacade.getInstance();
        AbstractFactory abstractFactory = PostgresFactory.createInstance();
        try {
            for (TransportCategory cat : itineraryFacade.findAllCatTransport() ) {
                this.nameCatTransport.add(cat.getNameTransportCat());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DBNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.selectCategory.getItems().addAll(this.nameCatTransport);

    }

    public void onCatTransportButtonClicked(ActionEvent event) {

    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {

    }


}
