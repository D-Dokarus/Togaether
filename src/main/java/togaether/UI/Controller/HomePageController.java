package togaether.UI.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import togaether.BL.ChatClient;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Notification;
import togaether.BL.Model.Travel;
import togaether.UI.SceneController;

import java.io.IOException;
import java.util.ArrayList;

public class HomePageController {
  @FXML
  private Button notificationsButton;
  @FXML
  private Button settingsButton;
  @FXML
  private Button trophiesButton;
  @FXML
  private Button trophiesAdminButton;
  @FXML
  private Button friendsButton;
  @FXML
  private Button archivedTravelsButton;
  @FXML
  private Button createTravel;
  @FXML
  private ListView travelList;
  @FXML
  private Label labelError;

  @FXML
  protected void initialize() {
    this.loadTravels();
    //Cacher le bouton pour gérer les trophées si non admin
    this.trophiesAdminButton.setVisible(UserFacade.getInstance().getConnectedUser().getIsAdmin());
  }

  private void loadTravels() {
    TravelFacade travelFacade = TravelFacade.getInstance();
    ArrayList<Travel> travels = (ArrayList<Travel>) travelFacade.findTravelsByUserId(UserFacade.getInstance().getConnectedUser().getId());

    ObservableList<Travel> observableTravels = FXCollections.observableArrayList();
    for(Travel travel : travels){
      observableTravels.add(travel);
    }
    // We need to create a new CellFactory so we can display our layout for each individual notification
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

            //BUTTON GOTRAVEL
            Button btnGoTravel = new Button("Voir");
            btnGoTravel.setOnAction(event -> {
              TravelFacade.getInstance().setTravel(travel);
              SceneController.getInstance().switchToTravel(event);
            });
            //BUTTON ARCHIVETRAVEL
            Button btnArchiveTravel = new Button("Archiver");
            btnArchiveTravel.setOnAction(event -> {
              TravelFacade.getInstance().setTravel(travel);
              SceneController.getInstance().switchToArchiveTravel(event);
            });
            //BUTTON DELETETRAVEL
            Button btnDeleteTravel = new Button("Supprimer");
            btnDeleteTravel.setOnAction(event -> {
              TravelFacade.getInstance().setTravel(travel);
              //TO DO pop-up delete
            });
            root.getChildren().addAll(btnGoTravel, btnArchiveTravel, btnDeleteTravel);

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

  public void onNotificationsButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToNotificationCenter(event);
  }
  public void onSettingsButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToSettings(event);
  }
  public void onTrophiesButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToTrophy(event);
  }
  public void onTrophiesAdminButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToAdminTrophy(event);
  }
  public void onFriendsButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToFriend(event);
  }
  public void onArchivedTravelsButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToListArchivedTravel(event);
  }
  public void onCreateTravelButtonClicked(ActionEvent event) { SceneController.getInstance().switchToCreateTravel(event);}
  public void onSearchTravelButtonClicked(ActionEvent event) {
    // FAIRE LA RECHERCHE
  }

  public void error(Exception e) {
    this.labelError.setText("Problème lors du chargement de la page, veuillez réessayer plus tard...");
  }

}
