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
import togaether.BL.Facade.NotificationFacade;
import togaether.BL.TogaetherException.*;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.User;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChooseCollaboratorController {

    @FXML
    private ListView<Collaborator> collaboratorsListView;
    List<Collaborator> collaborators = new ArrayList<>();
    ObservableList<Collaborator> observableCollaborators = FXCollections.observableArrayList();
    @FXML
    private Button buttonGoBack;

    @FXML
    protected void initialize() {
        try{
            collaborators = CollaboratorFacade.getInstance().findCollaboratorNotChosenByTravel(TravelFacade.getInstance().getTravel());
        }catch(CollaboratorNotFoundException e){
            System.out.println(e);
        }
        for(Collaborator collaborator : collaborators){
            System.out.println(collaborator.getName());
        }
        initializeCollaboratorsList();


    }

    /**
     * Initialize the listViewCollaborators with the list of collaborator "collaborators" already filled by setting up the behavior of a cell;
     */
    public void initializeCollaboratorsList(){
        observableCollaborators.clear();
        for(Collaborator collaborator: collaborators){
            observableCollaborators.add(collaborator);
        }
        // We need to create a new CellFactory so we can display our layout for each individual notification
        collaboratorsListView.setCellFactory((Callback<ListView<Collaborator>, ListCell<Collaborator>>) param -> {
            return new ListCell<Collaborator>() {
                @Override
                protected void updateItem(Collaborator collaborator, boolean empty) {
                    super.updateItem(collaborator, empty);

                    if (collaborator == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        Label name = new Label(collaborator.getName());
                        name.setVisible(true);
                        name.setManaged(true);

                        root.getChildren().add(name);

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);


                        //BUTTON Validate
                        Button btnValidate = new Button("C'est moi !");
                        btnValidate.setOnAction(event -> {
                            collaborator.setUser(UserFacade.getInstance().getConnectedUser());
                            CollaboratorFacade.getInstance().updateCollaborator(collaborator);
                            onClickButtonToHomePage(event);
                            NotificationFacade.getInstance().deleteNotification(NotificationFacade.getInstance().getNotification());
                        });
                        root.getChildren().addAll(btnValidate);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
        collaboratorsListView.setItems(observableCollaborators);
    }


    /**
     * Switch to HomePage
     * @param event
     */
    public void onClickButtonToHomePage(ActionEvent event){
        SceneController.getInstance().switchToHomePage(event);
    }
}
