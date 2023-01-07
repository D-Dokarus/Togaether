package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import togaether.BL.Facade.*;
import togaether.BL.Model.*;
import togaether.UI.SceneController;

import java.sql.Date;
import java.sql.SQLException;
import java.time.*;
import java.util.*;
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
  @FXML
  ListView<Collaborator> participantList;

  //Ces maps permet de récupérer les id que l'on veut à partir des noms choisis par l'utilisateur via les listes
  Map<String, Integer> mapCategories;
  Map<String, Integer> mapParticipant = null;
  ObservableList<String> observableCategories;

  ArrayList<Collaborator> selectedParticipants;

  @FXML
  protected void initialize() {
    ArrayList<ExpenseCategory> categories;
    ArrayList<Collaborator> participants;
    try {
      //Récupérer les catégories de dépenses
      categories = (ArrayList<ExpenseCategory>) ExpenseFacade.getInstance().findAllCategories();
      this.mapCategories =
              categories.stream().collect(Collectors.toMap(ExpenseCategory::getName, ExpenseCategory::getId));
      this.observableCategories = FXCollections.observableArrayList();
      for(String name : mapCategories.keySet()) {
        observableCategories.add(ExpenseFacade.getInstance().nameToFrench(name));
      }
      this.selectCategory.setItems(this.observableCategories);
      this.selectCategory.getSelectionModel().select("Autres");

      //Récupérer les participants à la dépense (si modification et pas création)
      if(ExpenseFacade.getInstance().getExpense() != null) {
        this.selectedParticipants = (ArrayList<Collaborator>) ExpenseFacade.getInstance().findParticipantsByExpense(ExpenseFacade.getInstance().getExpense());
        this.mapParticipant =
                this.selectedParticipants.stream().collect(Collectors.toMap(Collaborator::getName, Collaborator::getId));
      }
    } catch (SQLException e) {
      System.out.println(e);
      this.displayInfo("Erreur avec la base de données, veuillez réessayer");
    }

    if(ExpenseFacade.getInstance().getExpense() != null) {
      this.nameExpense.setText(ExpenseFacade.getInstance().getExpense().getName());
      this.valueExpense.setText(ExpenseFacade.getInstance().getExpense().getValue()+"");
      LocalDate dateLocal = Instant.ofEpochMilli(ExpenseFacade.getInstance().getExpense().getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
      this.dateExpense.setValue(dateLocal);
      this.selectCategory.getSelectionModel().select(ExpenseFacade.getInstance().nameToFrench(ExpenseFacade.getInstance().getExpense().getCategory().getName()));
    }

    this.initializeParticipants();
  }

  public void initializeParticipants() {
    ObservableList<Collaborator> observableCollaborator = FXCollections.observableArrayList();
    observableCollaborator.addAll(TravelFacade.getInstance().getCollaborators());
    this.participantList.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Collaborator collaborator, boolean empty) {
        super.updateItem(collaborator, empty);

        if (collaborator == null || empty) {
          setText(null);
        } else {
          HBox root = new HBox(1);
          CheckBox checkBox = new CheckBox(collaborator.getName());
          //Vérifier si le collaborateur est déjà un participant
          if(mapParticipant != null)
            if(mapParticipant.get(collaborator.getName()) != null)
              checkBox.setSelected(true);

          //Quand elle sera cochée, elle ajoutera le collaborateur à la liste des participants
          checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
              selectedParticipants.add(collaborator);
            else
              selectedParticipants.remove(collaborator);

          });

          root.getChildren().add(checkBox);
          setText(null);
          setGraphic(root);
        }
      }
    });
    participantList.setItems(observableCollaborator);
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
        //Récupérer les infos
        String category = ExpenseFacade.getInstance().frenchToName(this.selectCategory.getSelectionModel().getSelectedItem());
        int category_id = this.mapCategories.get(category);
        Double value = Double.valueOf(this.valueExpense.getText());
        String name = this.nameExpense.getText();
        Date date = java.sql.Date.valueOf(this.dateExpense.getValue());

        //Créer ou modifier la dépense
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

        //Maintenant il faut ajouter les nouveaux participants
        for (Collaborator collaborator : this.selectedParticipants) {
          //null == on a pas de participant (car on crée la dépense) donc on peut ajouter le participant sans vérifier qu'il l'était déjà
          if(mapParticipant == null) {
            ExpenseFacade.getInstance().addParticipant(ExpenseFacade.getInstance().getExpense().getId(), collaborator.getId());
          }
          //Sinon, il faut vérifier s'il n'était pas déjà dans la liste
          else if(mapParticipant.get(collaborator.getName()) == null) {
            ExpenseFacade.getInstance().addParticipant(ExpenseFacade.getInstance().getExpense().getId(), collaborator.getId());
          }
        }
        //et enfin, enlever les ex-participants
        if(mapParticipant != null) {
          for (Map.Entry<String, Integer> entry : this.mapParticipant.entrySet()) {
            //Il faut donc vérifier s'ils sont dans selectedParticipants, et s'ils n'y sont plus, on retire de la base de données
            boolean found = false;
            for (Collaborator collaborator : this.selectedParticipants) {
              if (collaborator.getId() == entry.getValue()) found = true;
            }
            if(!found) ExpenseFacade.getInstance().removeParticipant(ExpenseFacade.getInstance().getExpense().getId(), entry.getValue());
          }
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
