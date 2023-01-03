package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.Trophy;
import togaether.UI.SceneController;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class TrophyController {
  @FXML
  FlowPane flowPane;
  @FXML
  Button returnButton;

  @FXML
  protected void initialize() {
    this.loadTrophies();
  }
  public void loadTrophies() {
    TrophyFacade trophyFacade = TrophyFacade.getInstance();
    try {
      ArrayList<Trophy> trophies = (ArrayList<Trophy>) trophyFacade.getAllTrophies();
      ArrayList<Trophy> tempOwned = (ArrayList<Trophy>) trophyFacade.getTrophiesOfUser();
      Map<Integer, Trophy> mapOwnedTrophies =
              tempOwned.stream().collect(Collectors.toMap(Trophy::getId, item -> item));

      for (Trophy trophy : trophies) {
        //Si l'utilisateur a ce trophée
        if(mapOwnedTrophies.containsKey(trophy.getId())) {
          addOwnedTrophy(trophy);
        }else {
          if(trophyFacade.isTrophyValidForUser(trophy)) {
            trophyFacade.giveTrophyToUser(trophy.getId());
            addOwnedTrophy(trophy);
          }
          else
            addNotOwnedTrophy(trophy);
        }
      }
    } catch (SQLException e) {
      System.out.println("Erreur lors de la récupération des succès");
    }
  }

  public void addOwnedTrophy(Trophy trophy) {
    VBox root = new VBox(2);
    root.setAlignment(Pos.CENTER_LEFT);
    root.setPadding(new Insets(5, 10, 5, 10));

    // Within the root, we'll show the username on the left and our two buttons to the right
    try {
      root.getChildren().add(new ImageView(new Image(new File(trophy.getImage()).toURI().toURL().toExternalForm())));
    } catch (MalformedURLException e) {
      //TO DO image par défaut
      root.getChildren().add(null);
    }
    HBox infos = new HBox(3);
    root.getChildren().add(infos);
    infos.getChildren().add(new Label(trophy.getName()));
    infos.getChildren().add(new Label(TrophyFacade.getInstance().getCondition(trophy.getCategory())));
    infos.getChildren().add(new Label(Integer.toString(trophy.getValue())));
    root.setStyle("-fx-background-color: green;");
    this.flowPane.getChildren().add(root);
  }
  public void addNotOwnedTrophy(Trophy trophy) {
    VBox root = new VBox(2);
    root.setAlignment(Pos.CENTER_LEFT);
    root.setPadding(new Insets(5, 10, 5, 10));

    // Within the root, we'll show the username on the left and our two buttons to the right
    try {
      root.getChildren().add(new ImageView(new Image(new File(trophy.getImage()).toURI().toURL().toExternalForm())));
    } catch (MalformedURLException e) {
      //TO DO image par défaut
      root.getChildren().add(null);
    }
    HBox infos = new HBox(3);
    root.getChildren().add(infos);
    infos.getChildren().add(new Label(trophy.getName()));
    infos.getChildren().add(new Label(TrophyFacade.getInstance().getCondition(trophy.getCategory())));
    infos.getChildren().add(new Label(Integer.toString(trophy.getValue())));
    this.flowPane.getChildren().add(root);
  }

  public void onReturnButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToHomePage(event);
  }
}
