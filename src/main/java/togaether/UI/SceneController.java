package togaether.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import togaether.App;
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
  public void switchToDeleteAccount(ActionEvent event) {
    App.getInstance().switchScene("DeleteAccount", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToTrophy(ActionEvent event) {
    App.getInstance().switchScene("Trophy", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToFriend(ActionEvent event) {
    App.getInstance().switchScene("Friend", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToArchiveTravel(ActionEvent event) {
    App.getInstance().switchScene("ArchiveTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToUnarchiveTravel(ActionEvent event) {
    App.getInstance().switchScene("UnarchiveTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToListArchivedTravel(ActionEvent event) {
    App.getInstance().switchScene("ListArchivedTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }
  public void switchToCreateTravel(ActionEvent event) {
    App.getInstance().switchScene("CreateTravel", (Stage)((Node)event.getSource()).getScene().getWindow());
  }

  public void newPopupNotification(ActionEvent event){
    Parent root;
    try {
      root = FXMLLoader.load(NotificationFrame.class.getResource("NotificationCenter"+".fxml"));
      Stage stage = new Stage();
      stage.setTitle("NotificationCenter");
      stage.setScene(new Scene(root));
      stage.initModality(Modality.WINDOW_MODAL);
      stage.show();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
