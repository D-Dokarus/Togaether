package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import togaether.BL.Facade.ExpenseFacade;
import togaether.BL.Facade.TrophyFacade;
import togaether.UI.SceneController;

public class ExpenseDeleteController {
  @FXML
  private Label nameExpense;
  @FXML
  private Text valueExpense;
  @FXML
  private Label labelError;

  @FXML
  protected void initialize() {
    TrophyFacade trophyFacade = TrophyFacade.getInstance();

    this.nameExpense.setText(ExpenseFacade.getInstance().getExpense().getName());
    this.valueExpense.setText(ExpenseFacade.getInstance().getExpense().getValue()+"");
  }

  public void onConfirmedButtonClicked(ActionEvent event) {
    try {
      ExpenseFacade.getInstance().deleteExpense(ExpenseFacade.getInstance().getExpense());
      ExpenseFacade.getInstance().setExpense(null);
      SceneController.getInstance().switchToExpense(event);
      System.out.println("Dépense supprimée !");
    } catch (Exception e) {
      this.displayInfo("Attention : La dépense n'a pas pu être supprimée, veuillez réessayer");
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
