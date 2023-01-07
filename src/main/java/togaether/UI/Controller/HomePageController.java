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
  private Button unusable;
  @FXML
  private ListView<Travel> travelList;
  @FXML
  private Label labelError;

  @FXML
  protected void initialize() {
    this.loadTravels();
    //Cacher le bouton pour gérer les trophées si non admin
    this.trophiesAdminButton.setVisible(UserFacade.getInstance().getConnectedUser().getIsAdmin());
  }

  private void loadTravels() {
    this.unusable.setDisable(true);
    TravelFacade travelFacade = TravelFacade.getInstance();
    ArrayList<Travel> travels = (ArrayList<Travel>) travelFacade.findTravelsByUserId(UserFacade.getInstance().getConnectedUser().getId());

    ObservableList<Travel> observableTravels = FXCollections.observableArrayList();
    for(Travel travel : travels){
      observableTravels.add(travel);
    }

    this.travelList.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Travel travel, boolean empty) {
        super.updateItem(travel, empty);

        //TO DO ajouter un && avec un string de recherche de nom (si pas "")
        if (travel == null || empty) {
          setText(null);
        } else {

          HBox root = new HBox(10);
          root.setAlignment(Pos.CENTER_LEFT);
          root.setPadding(new Insets(5, 10, 5, 10));

          root.getChildren().add(new Label(travel.getNameTravel()));

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
            SceneController.getInstance().switchToDeleteTravel(event);
          });
          root.getChildren().addAll(btnGoTravel, btnArchiveTravel, btnDeleteTravel);

          setText(null);
          setGraphic(root);
        }
      }
    });
    travelList.setItems(observableTravels);
  }

  public void onNotificationsButtonClicked(ActionEvent event) {
    //SceneController.getInstance().switchToNotificationCenter(event);
    SceneController.getInstance().newPopupNotification(event);
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
