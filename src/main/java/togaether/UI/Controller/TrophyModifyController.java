package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import togaether.BL.Facade.TrophyFacade;
import togaether.BL.Model.TrophyCategory;
import togaether.BL.Model.Trophy;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class TrophyModifyController {
  @FXML
  Button returnButton;
  @FXML
  Button confirmedButton;
  @FXML
  Label labelError;
  @FXML
  TextArea nameTrophy;
  @FXML
  TextArea valueTrophy;
  @FXML
  TextArea imageTrophy;
  @FXML
  ChoiceBox<String> selectCategory;

  Map<String, Integer> mapCategories;
  ObservableList<String> observableCategories;

  @FXML
  protected void initialize() {
    ArrayList<TrophyCategory> categories;
    try {
      categories = (ArrayList<TrophyCategory>) TrophyFacade.getInstance().getAllCategories();
      this.mapCategories =
              categories.stream().collect(Collectors.toMap(TrophyCategory::getName, item -> ((TrophyCategory)item).getId()));
      this.observableCategories = FXCollections.observableArrayList();
      for(String name : mapCategories.keySet()) {
        observableCategories.add(TrophyFacade.getInstance().nameToFrench(name));
      }
      this.selectCategory.setItems(this.observableCategories);
      this.selectCategory.getSelectionModel().select(0);
    } catch (SQLException e) {
      this.displayInfo("Erreur avec la base de données, veuillez réessayer");
    }
    if(TrophyFacade.getInstance().getTrophy() != null) {
      this.nameTrophy.setText(TrophyFacade.getInstance().getTrophy().getName());
      this.valueTrophy.setText(String.valueOf(TrophyFacade.getInstance().getTrophy().getValue()));
      this.imageTrophy.setText(TrophyFacade.getInstance().getTrophy().getImage());
      this.selectCategory.getSelectionModel().select(TrophyFacade.getInstance().nameToFrench(TrophyFacade.getInstance().getTrophy().getCategory()));
    }
  }
  public void onConfirmedButtonClicked(ActionEvent event) {
    TrophyFacade trophyFacade = TrophyFacade.getInstance();
    if(this.nameTrophy.getText().length() < 1)
      this.displayInfo("Le nom ne peut pas être vide");
    else if (this.valueTrophy.getText().length() < 1 )
      this.displayInfo("La valeur ne peut pas être vide");
    else if (this.imageTrophy.getText().length() < 1 )
      this.displayInfo("Le nom de l'image ne peut pas être vide");
    else {

      try {
          String name = this.nameTrophy.getText();
          String category = TrophyFacade.getInstance().frenchToName(this.selectCategory.getSelectionModel().getSelectedItem());
          int category_id = this.mapCategories.get(category);
          int value = Integer.valueOf(this.valueTrophy.getText());
          String image = this.imageTrophy.getText();

        if(trophyFacade.getTrophy() == null) {
          Trophy trophy = new Trophy(0, name, category, category_id, value, image);
          trophyFacade.createTrophy(trophy);
        }
        else {
          Trophy trophy = trophyFacade.getTrophy();
          trophy.setName(name);
          trophy.setCategory(category);
          trophy.setCategory_id(category_id);
          trophy.setValue(value);
          trophy.setImage(image);
          trophyFacade.updateTrophy(trophy);

        }
        TrophyFacade.getInstance().setTrophy(null);
        SceneController.getInstance().switchToAdminTrophy(event);
        System.out.println("Trophée créer/modifié !");
      } catch (Exception e) {
        System.out.println(e);
        this.displayInfo("Attention : Les modifications n'ont pas pu être effectué, veuillez réessayer");
      }
    }
  }
  public void onReturnButtonClicked(ActionEvent event) {
    TrophyFacade.getInstance().setTrophy(null);
    SceneController.getInstance().switchToAdminTrophy(event);
  }

  public void displayInfo(String info) {
    this.labelError.setText(info);
  }
}
