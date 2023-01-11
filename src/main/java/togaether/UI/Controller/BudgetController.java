package togaether.UI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import togaether.BL.Facade.BudgetFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Budget;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BudgetController {

    @FXML
    private Label labelError;
    @FXML
    private Label labelError1;


    // CHECKBOXE
    @FXML
    private CheckBox checkboxFood;
    @FXML
    private CheckBox checkboxTransport;
    @FXML
    private CheckBox checkboxHealth;
    @FXML
    private CheckBox checkboxAccommodation;
    @FXML
    private CheckBox checkboxEntertainment;
    @FXML
    private CheckBox checkboxOther;

    // TextArea for update budget
    @FXML
    private TextArea food;
    @FXML
    private TextArea transport;
    @FXML
    private TextArea health;
    @FXML
    private TextArea accommodation;
    @FXML
    private TextArea entertainment;
    @FXML
    private TextArea other;

    // Label Budget
    @FXML
    private Label FoodBudget;
    @FXML
    private Label TransportBudget;
    @FXML
    private Label HealthBudget;
    @FXML
    private Label AccommodationBudget;
    @FXML
    private Label EntertainmentBudget;
    @FXML
    private Label OtherBudget;

    // Button
    @FXML
    private Button ConfirmedButton;
    @FXML
    private Button UpdateButton;


    private BudgetFacade budgetFacade;
    private TravelFacade travelFacade;

    private List<Budget> budgetsList;

    private static final String PATTERN = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

    @FXML
    protected void initialize() {
        this.budgetFacade = BudgetFacade.getInstance();
        this.travelFacade = TravelFacade.getInstance();
        this.labelError.setVisible(false);
        this.labelError1.setVisible(false);
        this.ConfirmedButton.setVisible(false);
        try {
            this.budgetsList = this.budgetFacade.findBudgetSByCollaboratorId(this.travelFacade.getCollaborator().getId());
            System.out.println(budgetsList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int b = 0; b < this.budgetsList.size(); b++) {
        }
        init();
    }



    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToTravel(event);
    }

    public void onUpdateButtonClicked(ActionEvent event) {

        this.ConfirmedButton.setVisible(true);
        this.UpdateButton.setVisible(false);

        // Update
        this.food.setVisible(true);
        this.health.setVisible(true);
        this.transport.setVisible(true);
        this.other.setVisible(true);
        this.accommodation.setVisible(true);
        this.entertainment.setVisible(true);

        this.food.setDisable(true);
        this.health.setDisable(true);
        this.transport.setDisable(true);
        this.other.setDisable(true);
        this.accommodation.setDisable(true);
        this.entertainment.setDisable(true);

        this.checkboxEntertainment.setVisible(true);
        this.checkboxTransport.setVisible(true);
        this.checkboxAccommodation.setVisible(true);
        this.checkboxOther.setVisible(true);
        this.checkboxHealth.setVisible(true);
        this.checkboxFood.setVisible(true);

        // Read
        this.FoodBudget.setVisible(false);
        this.HealthBudget.setVisible(false);
        this.OtherBudget.setVisible(false);
        this.AccommodationBudget.setVisible(false);
        this.EntertainmentBudget.setVisible(false);
        this.TransportBudget.setVisible(false);

        // Limit
        if (this.budgetsList.get(0).getLimit() != -1){
            this.food.setText(Double.toString(this.budgetsList.get(0).getLimit()));
        } else {
            this.food.setText("");
        }

        if (this.budgetsList.get(1).getLimit() != -1){
            this.transport.setText(Double.toString(this.budgetsList.get(1).getLimit()));
        } else {
            this.transport.setText("");
        }

        if (this.budgetsList.get(2).getLimit() != -1){
            this.other.setText(Double.toString(this.budgetsList.get(2).getLimit()));
        } else {
            this.other.setText("");
        }

        if (this.budgetsList.get(3).getLimit() != -1){
            this.health.setText(Double.toString(this.budgetsList.get(3).getLimit()));
        } else {
            this.health.setText("");
        }

        if (this.budgetsList.get(4).getLimit() != -1){
            this.accommodation.setText(Double.toString(this.budgetsList.get(4).getLimit()));
        } else {
            this.accommodation.setText("");
        }

        if (this.budgetsList.get(5).getLimit() != -1){
            this.entertainment.setText(Double.toString(this.budgetsList.get(5).getLimit()));
        } else {
            this.entertainment.setText("");
        }



    }

    public void onConfirmedButtonClicked(ActionEvent event) {

        this.UpdateButton.setVisible(true);
        this.labelError.setVisible(false);
        this.labelError1.setVisible(false);


        this.travelFacade.getCollaborator();
        try {

            // If food update
            if (this.checkboxFood.isSelected()) {
                if (this.food.getText().trim().isBlank() || this.food.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.food.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetFood = Double.parseDouble(this.food.getText().trim());
                    System.out.println(limitBudgetFood);
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetFood, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 1);
                }
            }

            // If transport update
            if (this.checkboxTransport.isSelected()) {
                if (this.transport.getText().trim().isBlank() || this.transport.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.transport.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetTransport = Double.parseDouble(this.transport.getText().trim());
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetTransport, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 2);
                }
            }

            // If other update
            if (this.checkboxOther.isSelected()) {
                if (this.other.getText().trim().isBlank() || this.other.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.other.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetOther = Double.parseDouble(this.other.getText().trim());
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetOther, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 3);
                }
            }

            // If health update
            if (this.checkboxHealth.isSelected()) {
                if (this.health.getText().trim().isBlank() || this.health.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.health.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetHealth = Double.parseDouble(this.health.getText().trim());
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetHealth, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 4);
                }
            }

            // If accommodation update
            if (this.checkboxAccommodation.isSelected()) {
                if (this.accommodation.getText().trim().isBlank() || this.accommodation.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.accommodation.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetAccommodation = Double.parseDouble(this.accommodation.getText().trim());
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetAccommodation, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 5);
                }
            }

            // If entertainment update
            if (this.checkboxEntertainment.isSelected()) {
                if (this.entertainment.getText().trim().isBlank() || this.entertainment.getText().trim().isEmpty()) {
                    this.labelError.setVisible(true);
                } else if (!checkPattern(this.entertainment.getText().trim())) {
                    this.labelError1.setVisible(true);
                } else {
                    double limitBudgetEntertainment = Double.parseDouble(this.entertainment.getText().trim());
                    this.budgetFacade.updateLimitByCollaboratorAndTravelAndCategory(limitBudgetEntertainment, this.travelFacade.getCollaborator().getId(), this.travelFacade.getTravel().getIdTravel(), 6);
                }
            }
            if(!this.labelError.isVisible() && !this.labelError1.isVisible()){
                SceneController.getInstance().switchToBudget(event);
                System.out.println("Budget modifié !");
            }

        } catch (Exception e) {
            System.out.println("Attention : Le budget n'a pas pu être modifié, veuillez réessayer");
            throw new RuntimeException(e);
        }
    }

    private boolean checkPattern(String limitEnter) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(limitEnter);
        return matcher.matches();
    }

    public void init() {
        this.food.setVisible(false);
        this.health.setVisible(false);
        this.transport.setVisible(false);
        this.other.setVisible(false);
        this.accommodation.setVisible(false);
        this.entertainment.setVisible(false);
        this.checkboxEntertainment.setVisible(false);
        this.checkboxTransport.setVisible(false);
        this.checkboxAccommodation.setVisible(false);
        this.checkboxOther.setVisible(false);
        this.checkboxHealth.setVisible(false);
        this.checkboxFood.setVisible(false);


        if (this.budgetsList.get(0).getLimit() != -1){
            this.FoodBudget.setText(Double.toString(this.budgetsList.get(0).getLimit()));
        } else {
            this.FoodBudget.setText("Pas de budget");
        }

        if (this.budgetsList.get(1).getLimit() != -1){
            this.TransportBudget.setText(Double.toString(this.budgetsList.get(1).getLimit()));
        } else {
            this.TransportBudget.setText("Pas de budget");
        }

        if (this.budgetsList.get(2).getLimit() != -1){
            this.OtherBudget.setText(Double.toString(this.budgetsList.get(2).getLimit()));
        } else {
            this.OtherBudget.setText("Pas de budget");
        }

        if (this.budgetsList.get(3).getLimit() != -1){
            this.HealthBudget.setText(Double.toString(this.budgetsList.get(3).getLimit()));
        } else {
            this.HealthBudget.setText("Pas de budget");
        }

        if (this.budgetsList.get(4).getLimit() != -1){
            this.AccommodationBudget.setText(Double.toString(this.budgetsList.get(4).getLimit()));
        } else {
            this.AccommodationBudget.setText("Pas de budget");
        }

        if (this.budgetsList.get(5).getLimit() != -1){
            this.EntertainmentBudget.setText(Double.toString(this.budgetsList.get(5).getLimit()));
        } else {
            this.EntertainmentBudget.setText("Pas de budget");
        }
    }

    public void onCheckBoxButtonClickedOther(ActionEvent event) {
        if (this.checkboxOther.isSelected()) {
            this.other.setDisable(false);
            if(this.budgetsList.get(2).getLimit()!=-1){
                this.other.setText(Double.toString(this.budgetsList.get(2).getLimit()));
            }else{
                this.other.setText("");
            }
        } else {
            this.other.setDisable(true);
        }
    }

    public void onCheckBoxButtonClickedFood(ActionEvent event) {
        if (this.checkboxFood.isSelected()) {
            this.food.setDisable(false);
            if(this.budgetsList.get(0).getLimit()!=-1){
                this.food.setText(Double.toString(this.budgetsList.get(0).getLimit()));
            }else{
                this.food.setText("");
            }
        } else {
            this.food.setDisable(true);
        }
    }

    public void onCheckBoxButtonClickedTransport(ActionEvent event) {
        if (this.checkboxTransport.isSelected()) {
            this.transport.setDisable(false);
            if(this.budgetsList.get(1).getLimit()!=-1){
                this.transport.setText(Double.toString(this.budgetsList.get(1).getLimit()));
            }else{
                this.transport.setText("");
            }
        } else {
            this.transport.setDisable(true);
        }
    }

    public void onCheckBoxButtonClickedHealth(ActionEvent event) {
        if (this.checkboxHealth.isSelected()) {
            this.health.setDisable(false);
            if(this.budgetsList.get(3).getLimit()!=-1){
                this.health.setText(Double.toString(this.budgetsList.get(3).getLimit()));
            }else{
                this.health.setText("");
            }
        } else {
            this.health.setDisable(true);
        }
    }

    public void onCheckBoxButtonClickedAccommodation(ActionEvent event) {
        if (this.checkboxAccommodation.isSelected()) {
            this.accommodation.setDisable(false);
            if (this.budgetsList.get(4).getLimit()!=-1){
                this.accommodation.setText(Double.toString(this.budgetsList.get(4).getLimit()));
            }else{
                this.accommodation.setText("");
            }
        } else {
            this.accommodation.setDisable(true);
        }
    }

    public void onCheckBoxButtonClickedEntertainment(ActionEvent event) {
        if (this.checkboxEntertainment.isSelected()) {
            this.entertainment.setDisable(false);
            if(this.budgetsList.get(5).getLimit()!=-1){
                this.entertainment.setText(Double.toString(this.budgetsList.get(5).getLimit()));
            }else {
                this.entertainment.setText("");
            }
        } else {
            this.entertainment.setDisable(true);
        }
    }

}
