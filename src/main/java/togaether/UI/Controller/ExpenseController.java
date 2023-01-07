package togaether.UI.Controller;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import togaether.BL.Facade.*;
import togaether.BL.Model.*;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseController {
  @FXML
  Button returnButton;
  @FXML
  Button balanceButton;
  @FXML
  Button addButton;
  @FXML
  ListView<Expense> expenseList;
  @FXML
  Label totalTravelLabel;
  @FXML
  Label totalUserLabel;

  private double totalTravel = 0.0;
  private double totalUser = 0.0;
  private Map<Integer, String> idNameCollaborators;

  @FXML
  protected void initialize() {
    this.loadExpenses();
  }
  public void loadExpenses() {
    ExpenseFacade expenseFacade = ExpenseFacade.getInstance();
    ArrayList<Expense> expensesTravel = new ArrayList<>();
    totalTravelLabel.setText(String.valueOf(totalTravel));
    totalUserLabel.setText(String.valueOf(totalUser));

    try { //Récupérer Dépenses
      expensesTravel = (ArrayList<Expense>) expenseFacade.findExpensesByTravelId(TravelFacade.getInstance().getTravel().getIdTravel());
      for (Expense expense: expensesTravel) {
        totalTravel += expense.getValue();
        //Si vous êtes le pay master (collaborator_id) de la dépense
        if (expense.getPay_master() == TravelFacade.getInstance().getCollaborator().getId()) {
          totalUser += expense.getValue();
        }
      }
        totalTravelLabel.setText(String.valueOf(totalTravel));
        totalUserLabel.setText(String.valueOf(totalUser));
    } catch (SQLException e) {
      System.out.println("Erreur lors de la récupération des Dépenses : "+e);
    }

    //Collaborators, et map pour récupérer leur nom grâce à leur id
    ArrayList<Collaborator> tempCollaborators = (ArrayList<Collaborator>) TravelFacade.getInstance().getCollaborators();
    idNameCollaborators = tempCollaborators.stream().collect(Collectors.toMap(Collaborator::getId, Collaborator::getName));

    ObservableList<Expense> observableExpense = FXCollections.observableArrayList();
    for(Expense expense : expensesTravel){
      observableExpense.add(expense);
    }
    this.expenseList.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Expense expense, boolean empty) {
        super.updateItem(expense, empty);

        if (expense == null || empty) {
          setText(null);
        } else {
          VBox root = new VBox(2);

          HBox firstRow = new HBox(3);
          Region region1 = new Region();
          HBox.setHgrow(region1, Priority.ALWAYS);


          HBox secondRow = new HBox(3);
          Region region2 = new Region();
          HBox.setHgrow(region2, Priority.ALWAYS);


          Label infos1 = new Label(expense.getName() + " (" + ExpenseFacade.getInstance().nameToFrench(expense.getCategory().getName()) + ") -> " + expense.getValue());
          infos1.setAlignment(Pos.CENTER_LEFT);
          firstRow.getChildren().add(infos1);
          firstRow.getChildren().add(region1);

          Label infos2 = new Label("Payé par " + idNameCollaborators.get(expense.getPay_master()) + " le " + expense.getDate());
          infos2.setAlignment(Pos.CENTER_LEFT);
          secondRow.getChildren().add(infos2);
          secondRow.getChildren().add(region2);

          Button modifyButton = new Button("Modifier");
          firstRow.getChildren().add(modifyButton);
          modifyButton.setOnAction(event -> {
            ExpenseFacade.getInstance().setExpense(expense);
            SceneController.getInstance().switchToModifyExpense(event);
          });
          Button deleteButton = new Button("Supprimer");
          deleteButton.setOnAction(event -> {
            ExpenseFacade.getInstance().setExpense(expense);
            SceneController.getInstance().switchToDeleteExpense(event);
          });
          secondRow.getChildren().add(deleteButton);
          root.getChildren().addAll(firstRow, secondRow);
          setText(null);
          setGraphic(root);
        }
      }
    });
    this.expenseList.setItems(observableExpense);
  }

  public void onReturnButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToTravel(event);
  }
  public void onBalanceButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToBalance(event);
  }
  public void onAddButtonClicked(ActionEvent event) {
    ExpenseFacade.getInstance().setExpense(null);
    SceneController.getInstance().switchToModifyExpense(event);
  }
}
