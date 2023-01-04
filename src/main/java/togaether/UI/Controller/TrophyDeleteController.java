package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import togaether.BL.Facade.TrophyFacade;
import togaether.UI.SceneController;

public class TrophyDeleteController {
  @FXML
  private Label nameTrophy;
  @FXML
  private Text conditionTrophy;
  @FXML
  private Label labelError;

  @FXML
  protected void initialize() {
    TrophyFacade trophyFacade = TrophyFacade.getInstance();

    this.nameTrophy.setText(trophyFacade.getTrophy().getName());
    this.conditionTrophy.setText(trophyFacade.getCondition(trophyFacade.getTrophy()));
  }

  public void onConfirmedButtonClicked(ActionEvent event) {
    TrophyFacade trophyFacade = TrophyFacade.getInstance();
    try {
      trophyFacade.deleteTrophy(trophyFacade.getTrophy());
      SceneController.getInstance().switchToAdminTrophy(event);
      System.out.println("Trophée supprimé !");
    } catch (Exception e) {
      this.displayInfo("Attention : Le trophée n'a pas pu être supprimé, veuillez réessayer");
    }
  }

  public void onReturnButtonClicked(ActionEvent event) {
    SceneController.getInstance().switchToAdminTrophy(event);
  }

  public void displayInfo(String info) {
    this.labelError.setText(info);
  }
}
