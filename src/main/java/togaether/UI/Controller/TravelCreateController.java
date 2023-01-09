package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.*;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;
import togaether.DB.Postgres.PostgresFactory;
import togaether.UI.SceneController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    //A utiliser puis décommenter
    //A UTILISER MAXIME
    @FXML
    private TextField newCollaboratorInput;
    @FXML
    private TextField yourCollaboratorInput;
    @FXML
    private Button addCollaboratorButton;
    @FXML
    private ListView collaboratorListView;
    List<String> collaborators = new ArrayList<>();
    ObservableList<String> observableCollaborators = FXCollections.observableArrayList();


    @FXML
    protected void initialize() {
        UserFacade userFacade = UserFacade.getInstance();
        AbstractFactory abstractFactory = PostgresFactory.createInstance();
        initializeListView();
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onCancelButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToHomePage(event);
    }

    public void onConfirmedButtonClicked(ActionEvent event) {
        UserFacade userFacade = UserFacade.getInstance();

        if(!this.nameTravel.getText().isEmpty() && !this.descriptionTravel.getText().isEmpty() && !this.yourCollaboratorInput.getText().isBlank()){
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
                Travel newTravel = new Travel(userFacade.getConnectedUser(), this.nameTravel.getText(), this.descriptionTravel.getText(), start, end, false);

                TravelFacade travelFacade = TravelFacade.getInstance();
                try {
                    int idTravel = travelFacade.createTravel(newTravel);
                    Travel travelTemporary = new Travel(idTravel);
                    String yourName = yourCollaboratorInput.getText();


                    //Création de votre collaborateur
                    Collaborator you = new Collaborator(travelTemporary,UserFacade.getInstance().getConnectedUser(),yourName);
                    CollaboratorFacade.getInstance().createCollaborator(you);

                    //Création de tous les collaborateurs
                    for(String str : collaborators){
                        Collaborator newCollaborator = new Collaborator(travelTemporary,str);
                        CollaboratorFacade.getInstance().createCollaborator(newCollaborator);
                    }



                    /*
                    travelFacade.setTravel();
                    SceneController.getInstance().switchToTravel(event);*/
                    SceneController.getInstance().switchToHomePage(event);
                    System.out.println("Voyage créé !");
                } catch (Exception e) {
                    System.out.println("Attention : Le voyage n'a pas pu être créé, veuillez réessayer");
                    this.labelError.setText("Attention : Le voyage n'a pas pu être créé, veuillez réessayer");
                    throw new RuntimeException(e);
                }
            }


        } else {
            this.labelError.setText("Le nom, la description, et votre nom de collaborateur doivent être rempli !");
        }

    }

    public void initializeListView(){
        // We need to create a new CellFactory so we can display our layout for each individual notification
        collaboratorListView.setCellFactory((Callback<ListView<String>, ListCell<String>>) param -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String str, boolean empty) {
                    super.updateItem(str, empty);

                    if (str == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(str));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON REMOVE collaborator
                        Button btnRemove = new Button("X");
                        btnRemove.setOnAction(event -> {
                            collaborators.remove(str);
                            collaboratorListView.getItems().remove(str);
                            updateList();
                        });
                        root.getChildren().addAll(btnRemove);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
    }

    public void updateList(){

        observableCollaborators.clear();
        for(String str : collaborators){
            observableCollaborators.add(str);
        }
        // We need to create a new CellFactory so we can display our layout for each individual notification
        collaboratorListView.setCellFactory((Callback<ListView<String>, ListCell<String>>) param -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String str, boolean empty) {
                    super.updateItem(str, empty);

                    if (str == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(str));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON REMOVE collaborator
                        Button btnRemove = new Button("X");
                        btnRemove.setOnAction(event -> {
                            collaborators.remove(str);
                            collaboratorListView.getItems().remove(str);
                            updateList();
                        });
                        root.getChildren().addAll(btnRemove);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
        // Set our users to display in the ListView
        collaboratorListView.setItems(observableCollaborators);
    }

    public void onClickAddCollaborator(){
        if(!newCollaboratorInput.getText().isBlank()){
            String str = newCollaboratorInput.getText();
            collaborators.add(str);
            collaboratorListView.getItems().add(str);
        }
    }

    public void onClickAddYourselfAsCollaborator(){
        if(!yourCollaboratorInput.getText().isBlank()){
            String str = newCollaboratorInput.getText();
        }
    }
}
