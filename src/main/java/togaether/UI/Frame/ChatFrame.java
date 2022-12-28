package togaether.UI.Frame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import togaether.UI.Controller.ChatController;

import java.io.IOException;

public class ChatFrame  extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(LoginFrame.class.getResource("Chat.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
    stage.setTitle("Togaether - Chat");
    stage.setScene(scene);
    ChatController chatController = fxmlLoader.getController();
    stage.setOnHidden(e -> {
      chatController.close();
      Platform.exit();
    });
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
