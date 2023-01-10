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
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.Trophy;
import togaether.UI.SceneController;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminTrophyController {
    @FXML
    FlowPane flowPane;
    @FXML
    Button returnButton;
    @FXML
    Button addButton;
    @FXML
    Label labelInfo;

    @FXML
    protected void initialize() {
        this.loadTrophies();
    }

    public void loadTrophies() {
        TrophyFacade trophyFacade = TrophyFacade.getInstance();
        try {
            ArrayList<Trophy> trophies = (ArrayList<Trophy>) trophyFacade.getAllTrophies();

            for (Trophy trophy : trophies) {
                addTrophy(trophy);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des Trophées");
        }
    }

    public void addTrophy(Trophy trophy) {
        HBox root = new HBox(2);
        root.setMinSize(100, 50);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(5, 10, 5, 10));
        try {
            ImageView imageView = new ImageView(new Image(TrophyFacade.getInstance().getImagePath(trophy, true), 50, 50, false, false));
            root.getChildren().add(imageView);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            System.out.println("Erreur lors de la récupération de l'image");
        }
        VBox infos = new VBox(4);
        infos.setFillWidth(true);
        infos.getChildren().add(new Label(trophy.getName()));

        Label condition = new Label(TrophyFacade.getInstance().getCondition(trophy));
        condition.setWrapText(true);
        infos.getChildren().add(condition);

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> {
            TrophyFacade.getInstance().setTrophy(trophy);
            SceneController.getInstance().switchToDeleteTrophy(event);
        });
        Button modifyButton = new Button("Modifier");
        modifyButton.setOnAction(event -> {
            TrophyFacade.getInstance().setTrophy(trophy);
            SceneController.getInstance().switchToModifyTrophy(event);
        });

        root.getChildren().addAll(infos, modifyButton, deleteButton);
        this.flowPane.getChildren().add(root);
    }

    public void onAddButtonClicked(ActionEvent event) {
        TrophyFacade.getInstance().setTrophy(null);
        SceneController.getInstance().switchToModifyTrophy(event);
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }
}
