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
import togaether.BL.Facade.*;
import togaether.BL.Model.*;
import togaether.DB.CollaboratorDAO;
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

    @FXML
    private Label labelError;

    List<Collaborator> collaborators = new ArrayList<>();
    ObservableList<Collaborator> observableCollaborators = FXCollections.observableArrayList();

    List<Friend> friends = new ArrayList<>();
    ObservableList<Friend> observableFriends = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        //Chopper la liste des collaborateurs et l'initialiser
        collaborators = CollaboratorFacade.getInstance().findCollaboratorByTravel(TravelFacade.getInstance().getTravel());
        initializeCollaboratorsList();
        friends = FriendFacade.getInstance().findAllFriends(UserFacade.getInstance().getConnectedUser());
        initializeFriendsList();

    }

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

                        if( collaborator.getUser() != null && collaborator.getUser().getId() == UserFacade.getInstance().getConnectedUser().getId()){
                            root.getChildren().add(new Label("(Vous)"));
                        }
                        // Within the root, we'll show the username on the left and our two buttons to the right

                        TextField inputName = new TextField(collaborator.getName());
                        inputName.setVisible(false);
                        inputName.setManaged(false);
                        Label name = new Label(collaborator.getName());
                        name.setVisible(true);
                        name.setManaged(true);

                        root.getChildren().add(inputName);
                        root.getChildren().add(name);

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);


                        Button btnValidate = new Button("Valider");
                        btnValidate.setManaged(false);
                        btnValidate.setVisible(false);
                        Button btnEditable = new Button("Renommer");
                        btnEditable.setManaged(true);
                        btnEditable.setVisible(true);
                        //BUTTON Validate
                        btnValidate.setOnAction(event -> {


                            String value = inputName.getText();
                            if(!containsName(collaborators,value,collaborator)){
                                labelError.setText("");

                                inputName.setVisible(!inputName.isVisible());
                                name.setVisible(!name.isVisible());
                                inputName.setManaged(!inputName.isManaged());
                                name.setManaged(!name.isManaged());

                                btnValidate.setVisible(!btnValidate.isVisible());
                                btnValidate.setManaged(!btnValidate.isManaged());
                                btnEditable.setVisible(!btnEditable.isVisible());
                                btnEditable.setManaged(!btnEditable.isManaged());

                                collaborator.setName(inputName.getText());
                                CollaboratorFacade.getInstance().updateCollaborator(collaborator);
                                initializeCollaboratorsList();
                            }else{
                                labelError.setText("Vous ne pouvez pas avoir deux collaborateurs avec le même nom !");
                            }

                        });

                        //BUTTON Editable
                        btnEditable.setOnAction(event -> {
                            //inputName.setEditable(!inputName.isEditable());
                            inputName.setVisible(!inputName.isVisible());
                            name.setVisible(!name.isVisible());
                            inputName.setManaged(!inputName.isManaged());
                            name.setManaged(!name.isManaged());

                            btnValidate.setVisible(!btnValidate.isVisible());
                            btnValidate.setManaged(!btnValidate.isManaged());
                            btnEditable.setVisible(!btnEditable.isVisible());
                            btnEditable.setManaged(!btnEditable.isManaged());
                        });

                        root.getChildren().addAll(btnEditable,btnValidate);

                        //BUTTON REMOVE collaborator
                        if(collaborator.getUser() == null || collaborator.getUser().getId() != UserFacade.getInstance().getConnectedUser().getId()){
                            Button btnRemove = new Button("Supprimer");
                            btnRemove.setOnAction(event -> {
                                onClickButtonRemove(collaborator);
                                initializeCollaboratorsList();
                            });
                            root.getChildren().add(btnRemove);
                        }else{
                            Button inactiveBtn = new Button("Supprimer");
                            inactiveBtn.setVisible(false);
                            root.getChildren().add(inactiveBtn);
                        }

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
        collaboratorsListView.setItems(observableCollaborators);
    }


    public void initializeFriendsList(){
        observableFriends.clear();
        for(Friend friend: friends){
            observableFriends.add(friend);
        }
        // We need to create a new CellFactory so we can display our layout for each individual notification
        friendsListView.setCellFactory((Callback<ListView<Friend>, ListCell<Friend>>) param -> {
            return new ListCell<Friend>() {
                @Override
                protected void updateItem(Friend friend, boolean empty) {
                    super.updateItem(friend, empty);

                    if (friend == null || empty ) {
                        setText(null);
                    } else {

                        User you = null;
                        User other = null;
                        if(friend.getUser1().getId() == UserFacade.getInstance().getConnectedUser().getId()){
                            you = friend.getUser1();
                            other = friend.getUser2();
                        }else {
                            you = friend.getUser2();
                            other = friend.getUser1();
                        }

                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));


                        // Within the root, we'll show the username on the left and our two buttons to the right


                        Label name = new Label(other.getPseudo());
                        name.setVisible(true);
                        name.setManaged(true);

                        root.getChildren().add(name);

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON INVITER AU VOYAGE
                        Button btnAdd= new Button("Ajouter");
                        btnAdd.setOnAction(event -> {
                                onClickInviteFriend(friend);
                                btnAdd.setText("Ajouté");
                                btnAdd.setDisable(true);
                        });
                        root.getChildren().add(btnAdd);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
        friendsListView.setItems(observableFriends);
    }



    public void onClickButtonToTravel(ActionEvent event){
        SceneController.getInstance().switchToTravel(event);
    }

    public void onClickAddNewCollaborator(ActionEvent event){
        String name = collaboratorNameInput.getText();
        if(!name.isBlank()){
            Travel travel = TravelFacade.getInstance().getTravel();
            Collaborator collaborator = new Collaborator(travel,name);
            int id = CollaboratorFacade.getInstance().createCollaborator(collaborator);
            Collaborator toAdd = new Collaborator(id,travel,name);
            collaboratorsListView.getItems().add(toAdd);
            collaborators.add(toAdd);

        }
    }

    public void onClickButtonRemove(Collaborator collaborator){
        collaborators.remove(collaborator);
        collaboratorsListView.getItems().remove(collaborator);
        deleteInDataBase(collaborator);
    }
    public void deleteInDataBase(Collaborator collaborator){
        CollaboratorFacade.getInstance().deleteColaborator(collaborator);
    }

    public boolean containsName(final List<Collaborator> list, final String name, Collaborator collab){
        return list.stream().filter(o -> o.getId() != collab.getId()).filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }


    public void onClickInviteFriend(Friend friend){
        User you = null;
        User other = null;
        if(friend.getUser1().getId() == UserFacade.getInstance().getConnectedUser().getId()){
            you = friend.getUser1();
            other = friend.getUser2();
        }else{
            you = friend.getUser1();
            other = friend.getUser2();
        }
        Travel travel = TravelFacade.getInstance().getTravel();
        Notification notification = new Notification(other,you,you.getPseudo() + " vous invite à rejoindre le voyage : " + travel.getNameTravel(),true,NotificationCategory.createNotification("travelInvitation"),travel.getIdTravel());
        NotificationFacade.getInstance().createNotification(notification);
    }

    @FXML
    private void onUpdateSearchBar(){

        String user_name = friendSearchBar.getText().trim();
        if(!user_name.isBlank()){
            String test = "%"+user_name+"%";
            friends = FriendFacade.getInstance().findAllFriendsByPseudo(UserFacade.getInstance().getConnectedUser(), test);
            initializeFriendsList();
        }
    }




}
