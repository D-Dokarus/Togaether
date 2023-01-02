package togaether;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import togaether.UI.Frame.LoginFrame;

import java.io.IOException;

public class App extends Application {
  static App instance = null;

  private Parent root;
  private Scene scene;

  public void start(Stage stage) {
    try {
      this.root = FXMLLoader.load(LoginFrame.class.getResource("Login.fxml"));
      this.scene = new Scene(this.root);
      stage.setTitle("Togaether");
      stage.setScene(this.scene);
      stage.show();

    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  static public App getInstance() {
    return App.instance;
  }

  public void switchScene(String nameFxmlFile, Stage stage) {
    try {
      root = FXMLLoader.load(LoginFrame.class.getResource(nameFxmlFile+".fxml"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    App.instance = new App();
    launch();
  }
}
