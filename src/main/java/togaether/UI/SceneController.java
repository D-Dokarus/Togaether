package togaether.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import togaether.App;
import togaether.UI.Frame.LoginFrame;
import togaether.UI.Frame.NotificationFrame;

import java.io.IOException;

public class SceneController {
  public static SceneController instance = new SceneController();

  public static SceneController getInstance() {
    return instance;
  }

  public void switchToHomePage(ActionEvent event) {
    App.getInstance().switchScene("HomePage", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToNotificationCenter(ActionEvent event) {
    App.getInstance().switchScene("NotificationCenter", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToSettings(ActionEvent event) {
    App.getInstance().switchScene("Settings", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToChat(ActionEvent event) {
    App.getInstance().switchScene("Chat", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToRegister(ActionEvent event) {
    App.getInstance().switchScene("Register", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToLogin(ActionEvent event) {
    App.getInstance().switchScene("Login", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToTravel(ActionEvent event) {
    App.getInstance().switchScene("Travel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToUpdateTravel(ActionEvent event) {
    App.getInstance().switchScene("UpdateTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToDeleteTravel(ActionEvent event) {
    App.getInstance().switchScene("DeleteTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToDeleteAccount(ActionEvent event) {
    App.getInstance().switchScene("DeleteAccount", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToTrophy(ActionEvent event) {
    App.getInstance().switchScene("Trophy", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToAdminTrophy(ActionEvent event) {
    App.getInstance().switchScene("AdminTrophy", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToDeleteTrophy(ActionEvent event) {
    App.getInstance().switchScene("DeleteTrophy", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToModifyTrophy(ActionEvent event) {
    App.getInstance().switchScene("ModifyTrophy", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToExpense(ActionEvent event) {
    App.getInstance().switchScene("Expense", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToBalance(ActionEvent event) {
    App.getInstance().switchScene("Balance", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToDeleteExpense(ActionEvent event) {
    App.getInstance().switchScene("DeleteExpense", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToModifyExpense(ActionEvent event) {
    App.getInstance().switchScene("ModifyExpense", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToFriend(ActionEvent event) {
    App.getInstance().switchScene("Friends", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToArchiveTravel(ActionEvent event) {
    App.getInstance().switchScene("ArchiveTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToUnarchiveTravel(ActionEvent event) {
    App.getInstance().switchScene("UnarchiveTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToListArchivedTravel(ActionEvent event) {
    App.getInstance().switchScene("ArchiveListTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToCreateTravel(ActionEvent event) {
    App.getInstance().switchScene("CreateTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToCollaborator(ActionEvent event) {
    App.getInstance().switchScene("Collaborator", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToItinerary(ActionEvent event) {
    App.getInstance().switchScene("Itinerary", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToCreateItinerary(ActionEvent event) {
    App.getInstance().switchScene("ItineraryCreate", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToActivity(ActionEvent event) {
    App.getInstance().switchScene("Activity", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToDocument(ActionEvent event) {
    App.getInstance().switchScene("Document", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToCreateDocument(ActionEvent event) {
    App.getInstance().switchScene("CreateDocument", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void switchToBudget(ActionEvent event) {
    App.getInstance().switchScene("Budget", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void newPopupNotification(ActionEvent event){
    Parent root;
    try {
      root = FXMLLoader.load(NotificationFrame.class.getResource("NotificationCenter"+".fxml"));
      Stage stage = new Stage();
      stage.setTitle("NotificationCenter");
      Scene scene = new Scene(root);
      scene.getStylesheets().add(LoginFrame.class.getResource("application.css").toExternalForm());
      stage.setScene(scene);
      stage.initModality(Modality.WINDOW_MODAL);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void switchToCreateActivity(ActionEvent actionEvent) {
  App.getInstance().switchScene("CreateActivity", (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
  }
  public void switchToUpdateActivity(ActionEvent actionEvent) {
    App.getInstance().switchScene("UpdateActivity", (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
  }
  public void switchToDeleteActivity(ActionEvent actionEvent) {
    App.getInstance().switchScene("DeleteActivity", (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
  }

  public void switchToChooseCollaborator(){
    Parent root = App.getInstance().getRoot();
    App.getInstance().switchScene("ChooseCollaborator",(Stage)root.getScene().getWindow());
  }

}
