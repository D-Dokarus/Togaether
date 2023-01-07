package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import togaether.BL.Facade.ActivityFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Activity;
import togaether.UI.SceneController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ActivityUpdateController {

    //BUTTON
    @FXML
    private Button returnButton;
    @FXML
    private Button confirmedButton;

    //TEXTAREA
    @FXML
    private TextArea nameActivity;
    @FXML
    private TextArea descriptionActivity;
    @FXML
    private TextArea priceActivity;
    @FXML
    private TextArea placeActivity;

    //DATEPICKER
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    //LABEL
    @FXML
    private Label labelError;

    //CHECKBOX
    @FXML
    private CheckBox nonDefineStart;
    @FXML
    private CheckBox nonDefineEnd;


    /**
     * The connected Travel
     */
    Activity activity;

    @FXML
    protected void initialize() {
        // Récupérer activity connected
        ActivityFacade activityFacade = ActivityFacade.getInstance();
        try {
            this.activity = activityFacade.getActivity();
        } catch (Exception e) {
            System.out.println("Attention : L'activité n'a pas pu être trouvée, veuillez réessayer");
            this.labelError.setText("Attention : L'activité n'a pas pu être trouvée, veuillez réessayer");
            throw new RuntimeException(e);
        }
        // Remplir les champs avec les informations de l'activité
        this.nameActivity.setText(this.activity.getNameActivity());
        this.descriptionActivity.setText(this.activity.getDescriptionActivity());
        this.priceActivity.setText(String.valueOf(this.activity.getPriceActivity()));
        this.placeActivity.setText(this.activity.getAddressActivity());

        if (this.activity.getDateStart()!= null) {
            LocalDate dateLocal = Instant.ofEpochMilli(this.activity.getDateStart().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            this.dateStart.setValue(dateLocal);
        }
        if (this.activity.getDateEnd()!= null) {
            LocalDate dateLocal = Instant.ofEpochMilli(this.activity.getDateEnd().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            this.dateEnd.setValue(dateLocal);
        }
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToActivity(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        UserFacade userFacade = UserFacade.getInstance();

        if (!this.nameActivity.getText().isEmpty()) {
            Date start = null;
            Date end = null;
            Double price = 0.;

            if (!this.priceActivity.getText().isEmpty()) {
                //Check if the price is a number
                try {
                    //Remplace le , par un . pour la conversion en double
                    String priceString = this.priceActivity.getText().replace(",", ".");
                    price = Double.parseDouble(priceString);
                } catch (NumberFormatException e) {
                    this.labelError.setText("Attention : Le prix doit être un nombre.");
                    return;
                }

            }
            if (this.dateStart.getValue() != null) {
                start = java.sql.Date.valueOf(this.dateStart.getValue());
            }
            if (this.dateEnd.getValue() != null) {
                end = java.sql.Date.valueOf(this.dateEnd.getValue());
            }
            if (start != null && end != null && end.before(start)) {
                this.labelError.setText("Attention : La date de départ de l'activité \n précède la date de fin,\n veuillez les changer ou les laisser vide.");
            }
            if (start != null && TravelFacade.getInstance().getTravel().getDateStart().after(start)) {
                this.labelError.setText("Attention : La date de départ de l'activité \n précède la date de départ du voyage,\n veuillez les changer ou les laisser vide.");
            }
            if (end != null && TravelFacade.getInstance().getTravel().getDateEnd().before(end)) {
                this.labelError.setText("Attention : La date de fin de l'activité \n est après la date de fin du voyage,\n veuillez les changer ou les laisser vide.");
            }
            if (labelError.getText().isEmpty()) {
                // Récupérer le voyage associé à l'activité
                Activity majActivity = new Activity(this.nameActivity.getText(), this.descriptionActivity.getText(), this.placeActivity.getText(), start, end, price);
                ActivityFacade activityFacade = ActivityFacade.getInstance();
                try {
                    majActivity.setIdActivity(this.activity.getIdActivity());
                    System.out.println("idActivity : " + majActivity.getIdActivity());
                    activityFacade.updateActivity(majActivity);
                    SceneController.getInstance().switchToActivity(event);
                    System.out.println("Activité modifiée !");
                } catch (Exception e) {
                    System.out.println("Attention : L'activité n'a pas pu être modifiée, veuillez réessayer");
                    this.labelError.setText("Attention : L'activité' n'a pas pu être modifiée\n, veuillez réessayer");
                    throw new RuntimeException(e);
                }
            }


        } else {
            this.labelError.setText("Le nom de l'activité doit être rempli !");
        }

    }

}


