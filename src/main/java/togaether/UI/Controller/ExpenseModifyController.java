package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Facade.ExpenseFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.Expense;
import togaether.BL.Model.ExpenseCategory;
import togaether.BL.Model.Trophy;
import togaether.UI.SceneController;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseModifyController {
  @FXML
  Button returnButton;
  @FXML
  Button confirmedButton;
  @FXML
  Label labelError;
  @FXML
  TextArea nameExpense;
  @FXML
  TextArea valueExpense;
  @FXML
  DatePicker dateExpense;
  @FXML
  ChoiceBox<String> selectCategory;

  Map<String, Integer> mapCategories;
  ObservableList<String> observableCategories;

  @FXML
  protected void initialize() {
    ArrayList<ExpenseCategory> categories;
    try {
      categories = (ArrayList<ExpenseCategory>) ExpenseFacade.getInstance().findAllCategories();
      this.mapCategories =
              categories.stream().collect(Collectors.toMap(ExpenseCategory::getName, ExpenseCategory::getId));
      this.observableCategories = FXCollections.observableArrayList();
      for(String name : mapCategories.keySet()) {
        observableCategories.add(ExpenseFacade.getInstance().nameToFrench(name));
      }
      this.selectCategory.setItems(this.observableCategories);
      this.selectCategory.getSelectionModel().select("Autres");
    } catch (SQLException e) {
      this.displayInfo("Erreur avec la base de données, veuillez réessayer");
    }
    if(ExpenseFacade.getInstance().getExpense() != null) {
      this.nameExpense.setText(ExpenseFacade.getInstance().getExpense().getName());
      this.valueExpense.setText(ExpenseFacade.getInstance().getExpense().getValue()+"");
      LocalDate dateLocal = Instant.ofEpochMilli(ExpenseFacade.getInstance().getExpense().getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
      this.dateExpense.setValue(dateLocal);
      this.selectCategory.getSelectionModel().select(ExpenseFacade.getInstance().nameToFrench(ExpenseFacade.getInstance().getExpense().getCategory().getName()));
    }
  }
  public void onConfirmedButtonClicked(ActionEvent event) {
    if(this.nameExpense.getText().length() < 1)
      this.displayInfo("Le nom ne peut pas être vide");
    else if (this.valueExpense.getText().length() < 1 )
      this.displayInfo("Le montant ne peut pas être vide");
    else if (this.dateExpense.getValue() == null)
      this.displayInfo("Une date doit être spécifiée");
    else {

      try {
        String category = ExpenseFacade.getInstance().frenchToName(this.selectCategory.getSelectionModel().getSelectedItem());
        int category_id = this.mapCategories.get(category);
        Double value = Double.valueOf(this.valueExpense.getText());
        String name = this.nameExpense.getText();
        Date date = java.sql.Date.valueOf(this.dateExpense.getValue());

        if(ExpenseFacade.getInstance().getExpense() == null) {
          ExpenseFacade.getInstance().createExpense(TravelFacade.getInstance().getTravel().getIdTravel(), TravelFacade.getInstance().getCollaborator().getId(), category_id, value, name, date);
        }
        else {
          Expense expense = ExpenseFacade.getInstance().getExpense();
          expense.setName(name);
          expense.setCategory(new ExpenseCategory(category_id, category));
          expense.setValue(value);
          expense.setDate(date);
          ExpenseFacade.getInstance().updateExpense(expense);

        }
        ExpenseFacade.getInstance().setExpense(null);
        SceneController.getInstance().switchToExpense(event);
        System.out.println("Dépense créée/modifiée !");
      } catch (Exception e) {
        System.out.println(e);
        this.displayInfo("Attention : Les modifications n'ont pas pu être effectué, veuillez réessayer");
      }
    }
  }

  public void onReturnButtonClicked(ActionEvent event) {
    ExpenseFacade.getInstance().setExpense(null);
    SceneController.getInstance().switchToExpense(event);
  }

  public void displayInfo(String info) {
    this.labelError.setText(info);
  }
}
