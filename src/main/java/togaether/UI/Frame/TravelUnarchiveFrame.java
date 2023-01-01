package togaether.UI.Frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TravelUnarchiveFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginFrame.class.getResource("UnarchiveTravel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Togaether - Unarchive Travel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}