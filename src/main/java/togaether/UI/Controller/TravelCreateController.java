package togaether.UI.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;

import java.util.Date;

public class TravelCreateController {

    @FXML
    private Button returnButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmedButton;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;
    @FXML
    private TextArea nameTravel;
    @FXML
    private TextArea descriptionTravel;
    @FXML
    private Label labelError;


    public void onReturnButtonClicked() {
        //TO DO retourner HomePage
        System.exit(1);

    }

    public void onCancelButtonClicked() {
        //TO DO retourner HomePage
        System.exit(1);

    }

    public void onConfirmedButtonClicked() {
        if(!this.nameTravel.getText().isEmpty() && !this.descriptionTravel.getText().isEmpty()){
            Date start = null;
            Date end = null;
            if (this.dateStart.getValue() != null){
                start = java.sql.Date.valueOf(this.dateStart.getValue());
            }
            if (this.dateEnd.getValue() != null){
                end = java.sql.Date.valueOf(this.dateEnd.getValue());
            }
            if(start != null && end != null && end.before(start)){
                this.labelError.setText("Attention : La date de départ du voyage précède la date de fin,\n veuillez les changer ou les laisser vide.");
            } else {
                // Récupérer userconnected
                User owner = new User(1, "Lau", "lau@SE.com", "1234");
                Travel newTravel = new Travel(owner, this.nameTravel.getText(), this.descriptionTravel.getText(), start, end, false);

                TravelFacade travelFacade = TravelFacade.getInstance();
                try {
                    travelFacade.createTravel(newTravel);
                    // Aller à la page du nouveau voyage créé
                    System.out.println("Voyage créé !");
                } catch (Exception e) {
                    System.out.println("Attention : Le voyage n'a pas pu être créé, veuillez réessayer");
                    this.labelError.setText("Attention : Le voyage n'a pas pu être créé, veuillez réessayer");
                    throw new RuntimeException(e);
                }
            }
        } else {
            this.labelError.setText("Le nom et la description doivent être rempli !");
        }

    }

}
