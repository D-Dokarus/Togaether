package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import togaether.BL.Facade.CollaboratorFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Notification;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.UI.SceneController;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorController {

    @FXML
    private Button buttonToTravel;
    @FXML
    private ListView collaboratorsListView;
    @FXML
    private ListView friendsListView;
    @FXML
    private TextField collaboratorNameInput;
    @FXML
    private TextField friendSearchBar;
    @FXML
    private Button buttonAddCollaborator;

    List<Collaborator> collaborators = new ArrayList<>();
    ObservableList<Collaborator> observableCollaboratos = FXCollections.observableArrayList();

    List<User> friends = new ArrayList<>();
    ObservableList<User> observableFriends = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {

        //Chopper la liste des collaborateurs et l'initialiser
        //Chopper la liste des 10 premiers amis et l'initialiser

    }

    public void initializeCollaboratorsList(){

    }

    public void reloadCollaboratorsList(){

    }

    public void initializeFriendsList(){

    }

    public void reloadFriendsList(){

    }

    public void onClickButtonToTravel(ActionEvent event){
        SceneController.getInstance().switchToTravel(event);
    }

    public void onClickAddNewCollaborator(ActionEvent event){
        String name = collaboratorNameInput.getText();
        if(!name.isBlank()){
            Travel travel = TravelFacade.getInstance().getTravel();
            Collaborator collaborator = new Collaborator(travel,name);
            CollaboratorFacade.getInstance().createCollaborator(collaborator);
        }
    }

}
