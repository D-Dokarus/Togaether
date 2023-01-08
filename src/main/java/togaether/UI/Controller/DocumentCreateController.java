package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import togaether.BL.Facade.DocumentFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Document;
import togaether.DB.AbstractFactory;
import togaether.DB.Postgres.PostgresFactory;
import togaether.UI.SceneController;
import javax.swing.filechooser.*;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

public class DocumentCreateController {

    //TextArea
    @FXML
    private TextArea pathDocument;

    //Button
    @FXML
    private Button returnButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private Button browseButton;

    //ImageView
    @FXML
    private ImageView imageDocument;

    //Label
    @FXML
    private Label labelError;

    File file = null;
    String path = null;
    String fname = null;
    int s = 0;
    byte[] data = new byte[s];
    FileInputStream fis = null;

    @FXML
    protected void initialize() {
        UserFacade userFacade = UserFacade.getInstance();
        AbstractFactory abstractFactory = PostgresFactory.createInstance();
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToDocument(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        DocumentFacade documentFacade = DocumentFacade.getInstance();
        TravelFacade travelFacade = TravelFacade.getInstance();
        try {
            Document document = new Document(travelFacade.getTravel().getIdTravel(), fname, data, path);
            documentFacade.createDocument(document);
            SceneController.getInstance().switchToDocument(event);
        } catch (Exception e) {
            this.labelError.setText("Problème lors de la création du document,\n veuillez réessayer (Possible que le document existe déjà\n ou que le nom du document soit trop long).");

        };
    }

    private static double getFileSizeMegaBytes(File file) {
        return file.length() / (1024 * 1024);
    }

    public void onBrowseButtonClicked(ActionEvent event) throws FileNotFoundException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG, JPEG, PNG & PDF", "jpg", "png", "pdf", "jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            if (getFileSizeMegaBytes(file) < 1 ){
                path = file.getAbsolutePath();
                pathDocument.setText(file.getAbsolutePath());
                fname = file.getName();
                s = (int) file.length();
                data = new byte[s];
                fis = new FileInputStream(file);
                try {
                    fis.read(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.labelError.setText("Document trop volumineux, veuillez réessayer");
            }

        }
    }

}
