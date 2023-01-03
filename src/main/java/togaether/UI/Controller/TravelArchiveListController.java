package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import togaether.BL.Facade.DBNotFoundException;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Travel;
import togaether.UI.SceneController;

import java.util.ArrayList;
import java.util.List;

public class TravelArchiveListController {

    @FXML
    private Label labelError;
    @FXML
    private ListView travelList;
    @FXML
    private Button unusable;

    @FXML
    private Button returnButton;

    private List<Travel> travelsArchived;

    @FXML
    protected void initialize() {
        this.unusable.setDisable(true);
        TravelFacade travelFacade = TravelFacade.getInstance();
        UserFacade userFacade = UserFacade.getInstance();
        try {
            travelsArchived = (List<Travel>) travelFacade.travelsArchived(userFacade.getConnectedUser().getId());
        } catch (DBNotFoundException e) {
            this.labelError.setText("Problème lors du chargement des voyages archivés, veuillez réessayer plus tard...");
            throw new RuntimeException(e);
        }
        ObservableList<Travel> observableTravels = FXCollections.observableArrayList();
        for(Travel travel : travelsArchived){
            observableTravels.add(travel);
        }
        this.travelList.setCellFactory((Callback<ListView<Travel>, ListCell<Travel>>) param -> {
            return new ListCell<Travel>() {
                @Override
                protected void updateItem(Travel travel, boolean empty) {
                    super.updateItem(travel, empty);

                    //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                    if (travel == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(travel.getNameTravel()));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON ARCHIVETRAVEL
                        Button btnArchiveTravel = new Button("Désarchiver");
                        btnArchiveTravel.setOnAction(event -> {
                            TravelFacade.getInstance().setTravel(travel);
                            SceneController.getInstance().switchToUnarchiveTravel(event);
                        });
                        //BUTTON DELETETRAVEL
                        Button btnDeleteTravel = new Button("Supprimer");
                        btnDeleteTravel.setOnAction(event -> {
                            TravelFacade.getInstance().setTravel(travel);
                            //TO DO pop-up delete
                        });
                        root.getChildren().addAll(btnArchiveTravel, btnDeleteTravel);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });

        // Set our users to display in the ListView
        travelList.setItems(observableTravels);
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

}
