package togaether.UI.Frame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TravelArchiveFrame extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginFrame.class.getResource("ArchiveTravel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Togaether - Archive Travel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
