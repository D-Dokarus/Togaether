package togaether.UI.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.ItineraryFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Itinerary;
import togaether.BL.Model.TransportCategory;
import togaether.BL.Model.Travel;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.Postgres.PostgresFactory;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItineraryCreateController {

    @FXML
    private Button returnButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private DatePicker dateItinerary;
    @FXML
    private TextArea hourItinerary;
    @FXML
    private TextArea nameItinerary;
    @FXML
    private TextArea descriptionItinerary;
    @FXML
    private Label labelError;
    @FXML
    private Label labelErrorHour;
    @FXML
    private ChoiceBox<String> selectCategory;
    @FXML
    private RadioButton CatTransportCar;
    @FXML
    private RadioButton CatTransportPlane;
    @FXML
    private RadioButton CatTransportBus;
    @FXML
    private RadioButton CatTransportTrain;
    @FXML
    private RadioButton CatTransportBoat;
    @FXML
    private RadioButton CatTransportWalk;

    private List<String> nameCatTransport = new ArrayList<String>();

    private TravelFacade travelFacade;
    private ItineraryFacade itineraryFacade;

    private int catSelected = 0;

    private ToggleGroup tg = new ToggleGroup();

    private int catNewItinerary = -1;

    private int isSelected = -1; // -1 for false, 0 for PtInteret, 1 for Transport

    private static final String PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private boolean correctHour = false;

    @FXML
    protected void initialize() {
        this.itineraryFacade = ItineraryFacade.getInstance();
        this.travelFacade = TravelFacade.getInstance();
        initializeView();
    }

    public void initializeView(){

        try {
            for (TransportCategory cat : itineraryFacade.findAllCatTransport() ) {
                this.nameCatTransport.add(cat.getNameTransportCat());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DBNotFoundException e) {
            throw new RuntimeException(e);
        }
        // this.selectCategory.getItems().addAll(this.nameCatTransport);
        String NothingSelect = "Choisissez le type d'étape";
        String PtInteret = "Ajouter un Point d'intérêt";
        String transport = "Ajouter un moyen de transport";
        this.selectCategory.getItems().add(NothingSelect);
        this.selectCategory.getItems().add(PtInteret);
        this.selectCategory.getItems().add(transport);
        this.selectCategory.getSelectionModel().select(0); // Select PtInteret by default

        // ButtonRadio
        this.CatTransportCar.setToggleGroup(tg);
        this.CatTransportBoat.setToggleGroup(tg);
        this.CatTransportBus.setToggleGroup(tg);
        this.CatTransportPlane.setToggleGroup(tg);
        this.CatTransportTrain.setToggleGroup(tg);
        this.CatTransportWalk.setToggleGroup(tg);
        this.CatTransportCar.setSelected(true);
        // hidden
        this.CatTransportCar.setVisible(false);
        this.CatTransportBoat.setVisible(false);
        this.CatTransportBus.setVisible(false);
        this.CatTransportPlane.setVisible(false);
        this.CatTransportTrain.setVisible(false);
        this.CatTransportWalk.setVisible(false);
    }

    public void onTypeCatSelected(ActionEvent event) {
        if(this.selectCategory.getValue() == "Ajouter un Point d'intérêt"){
            isSelected = 0;
            this.CatTransportCar.setVisible(false);
            this.CatTransportBoat.setVisible(false);
            this.CatTransportBus.setVisible(false);
            this.CatTransportPlane.setVisible(false);
            this.CatTransportTrain.setVisible(false);
            this.CatTransportWalk.setVisible(false);
        } else if (this.selectCategory.getValue() == "Ajouter un moyen de transport"){
            isSelected = 1;
            this.CatTransportCar.setVisible(true);
            this.CatTransportBoat.setVisible(true);
            this.CatTransportBus.setVisible(true);
            this.CatTransportPlane.setVisible(true);
            this.CatTransportTrain.setVisible(true);
            this.CatTransportWalk.setVisible(true);

        }else {
            isSelected = -1;
        }
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToItinerary(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        // if it's a itinerary type transport
        if(!this.nameItinerary.getText().isEmpty() && !this.descriptionItinerary.getText().isEmpty()){
            if(isSelected == 1){
                RadioButton rb = (RadioButton) tg.getSelectedToggle();
                switch (rb.getText()){
                    case "Voiture 🚗":
                        this.catSelected = 1; // "Car"
                        break;
                    case "Avion ✈":
                        this.catSelected = 2; // "Plane"
                        break;
                    case "Bus 🚌":
                        this.catSelected = 3; // "Bus"
                        break;
                    case "Train 🚂":
                        this.catSelected = 4; // "Train"
                        break;
                    case "Bateau ⛵":
                        this.catSelected = 5; // "Boat"
                        break;
                    case "Pied 🚶":
                        this.catSelected = 6; // "Walk"
                        break;
                }
            }

            LinkedList<Itinerary> linkedList = this.travelFacade.findItineraries(this.travelFacade.getTravel().getIdTravel());
            int indexBeforeItinerary = -1;
            if(linkedList.size()!=0){
                indexBeforeItinerary = linkedList.getLast().getItinerary_id();
            }

            Date dateNewItinerary = null;
            if(this.dateItinerary.getValue() != null)
            dateNewItinerary = java.sql.Date.valueOf(this.dateItinerary.getValue());

            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(this.hourItinerary.getText());
            if(matcher.matches()){
                this.correctHour = true;
            }
            if((correctHour || this.hourItinerary.getText().isEmpty())){
                try {
                    Itinerary iti = new Itinerary(this.travelFacade.getTravel().getIdTravel(),this.nameItinerary.getText(),dateNewItinerary,indexBeforeItinerary,this.catSelected,this.descriptionItinerary.getText(), this.hourItinerary.getText(),-1);
                    int ret = itineraryFacade.createItinerary(iti);
                    Itinerary itiResult = itineraryFacade.findItineraryById(ret);
                    if(linkedList.size()!=0){
                        itineraryFacade.updateIndexAfterItineraryById(linkedList.getLast().getItinerary_id(), itiResult.getItinerary_id());
                    }
                    //this.travelFacade.getItineraries().add(itiResult);
                    System.out.println("Itineraire créé !");
                    SceneController.getInstance().switchToItinerary(event);
                } catch (Exception e) {
                    System.out.println("Attention : L'itineraire n'a pas pu être créé, veuillez réessayer");
                    throw new RuntimeException(e);
                }
            }else {
                this.labelErrorHour.setText("L'heure n'est pas au bon format,\n pour rappel le bon format est 00:00");
            }
        } else {
            this.labelError.setText("Le nom et la description de votre itinéraire doivent être rempli !");
        }
    }

    // END OF CLASS
}
