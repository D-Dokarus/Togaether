package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.Trophy;
import togaether.UI.SceneController;

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
          addTrophy(trophy, true);
        }else {
          addTrophy(trophy, false);
        }
      }
    } catch (SQLException e) {
      System.out.println("Erreur lors de la récupération des Trophées");
    }
  }

  public void addTrophy(Trophy trophy, Boolean isOwned) {
    HBox root = new HBox(2);
    root.setMinSize(100, 50);
    root.setAlignment(Pos.CENTER);
    root.setPadding(new Insets(5, 10, 5, 10));

    ImageView imageView = new ImageView(new Image(TrophyFacade.getInstance().getImagePath(trophy, isOwned), 50, 50, false, false));
    root.getChildren().add(imageView);

    VBox infos = new VBox(2);
    infos.setFillWidth(true);
    infos.getChildren().add(new Label(trophy.getName()));

    Label condition = new Label(TrophyFacade.getInstance().getCondition(trophy));
    condition.setWrapText(true);
    infos.getChildren().add(condition);

    root.getChildren().add(infos);
    this.flowPane.getChildren().add(root);
  }

  public void onReturnButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToHomePage(event);
  }
}
