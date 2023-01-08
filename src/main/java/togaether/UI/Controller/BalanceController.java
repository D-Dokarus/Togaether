package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import togaether.BL.Facade.ExpenseFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Expense;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceController {
  @FXML
  Button travelButton;
  @FXML
  Button expenseButton;
  @FXML
  Label labelError;
  @FXML
  ListView<Collaborator> list;

  @FXML
  protected void initialize() {
    //On va en 2 temps, pour chaque collaborateur :
    // - Faire la somme des participations (sommer les montant de chaque dépense auquel il participe, diviser par le nombre de personne qui participe à cette dépense)
    // - Faire la somme des dépenses pour lesquels le collaborateur est le pay master
    // - On affiche au final la soustraction des deux

    ObservableList<Collaborator> observableCollaborator = FXCollections.observableArrayList();
    observableCollaborator.addAll(TravelFacade.getInstance().getCollaborators());

    Map<Integer, Double> mapLost = new HashMap<>();
    Map<Integer, Double> mapGain = new HashMap<>();
    try {
      for (Collaborator collaborator: TravelFacade.getInstance().getCollaborators()) {
        //étape 1 :
        double lost = ExpenseFacade.getInstance().getSumExpenseToPaidByCollaboratorId(collaborator.getId());
        mapLost.put(collaborator.getId(), lost);
        //étape 2 :
        double gain = ExpenseFacade.getInstance().getSumExpenseToGainByCollaboratorId(collaborator.getId());
        mapGain.put(collaborator.getId(), gain);
      }
    } catch (SQLException e) {
      System.out.println(e);
      displayInfo("Erreur lors de la récupération des infos de dépenses");
    }

    //On affiche tout
    this.list.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Collaborator collaborator, boolean empty) {
        super.updateItem(collaborator, empty);

        if (collaborator == null || empty) {
          setText(null);
        } else {
          HBox root = new HBox(2);
          // gain - lost
          double total = mapGain.get(collaborator.getId()) - mapLost.get(collaborator.getId());
          root.getChildren().add(new Label(collaborator.getName()+" : "+new DecimalFormat("##.##").format(total)));
          if(total < 0)
            root.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
          else
            root.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
          setText(null);
          setGraphic(root);
        }
      }
    });
    this.list.setItems(observableCollaborator);
  }

 public void onTravelButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToTravel(event);
 }
  public void onExpenseButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToExpense(event);
  }

  public void displayInfo(String info) {
    this.labelError.setText(info);
  }
}
