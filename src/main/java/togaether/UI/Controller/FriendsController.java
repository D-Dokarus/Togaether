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
import togaether.UI.SceneController;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class FriendsController {

    @FXML
    private Button btnGoBack;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<Friend> friendsListView;
    @FXML
    private SplitMenuButton splitMenuButton;
    @FXML
    private ListView<User> usersListView;

    List<Friend> friends = new ArrayList<>();
    ObservableList<Friend> observableFriends = FXCollections.observableArrayList();

    List<User> users = new ArrayList<>();
    ObservableList<User> observableUsers = FXCollections.observableArrayList();


    @FXML
    protected void initialize() {
        //Pour faire en sorte de faire un appel BD à chaque modification de la barre de recherche
        /*searchBar.textProperty().addListener((obs) -> {
            onUpdateSearchBar();
        });*/
        friends = FriendFacade.getInstance().findAllFriends(UserFacade.getInstance().getConnectedUser());
        initializeFriendsList();
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

                    if (friend == null || empty) {
                        setText(null);
                    } else {
                        User other = null;
                        if(friend.getUser1().getId() == UserFacade.getInstance().getConnectedUser().getId()){
                            other = friend.getUser2();
                        }else{
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

                        //BUTTON REMOVE collaborator

                        Button btnRemove = new Button("Supprimer");
                        btnRemove.setOnAction(event -> {
                            onClickButtonRemove(friend);
                            initializeFriendsList();
                        });
                        root.getChildren().add(btnRemove);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }
                }
            };

        });
        friendsListView.setItems(observableFriends);
    }

    public void initializeUsersList(){
        observableUsers.clear();
        for(User user: users){
            observableUsers.add(user);
        }
        // We need to create a new CellFactory so we can display our layout for each individual notification
        usersListView.setCellFactory((Callback<ListView<User>, ListCell<User>>) param -> {
            return new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);

                    if (user == null || empty) {
                        setText(null);
                    } else {

                        assert(user.getId() != UserFacade.getInstance().getConnectedUser().getId());

                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right

                        Label name = new Label(user.getPseudo());
                        name.setVisible(true);
                        name.setManaged(true);

                        root.getChildren().add(name);

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON ADD user
                        Button btnAdd = new Button("Ajouter");
                        btnAdd.setOnAction(event -> {
                            onClickButtonAdd(user);
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
        usersListView.setItems(observableUsers);
    }


    private void onClickButtonRemove(Friend friend){
        friends.remove(friend);
        friendsListView.getItems().remove(friend);
        FriendFacade.getInstance().deleteFriend(friend);
    }

    @FXML
    private void onUpdateSearchBar(){

        String user_name = searchBar.getText().trim();
        if(user_name.length() >= 3 && !user_name.isBlank()){
            String test = "%"+user_name+"%";
            users = UserFacade.getInstance().findAllUsersByPseudo(test);
            initializeUsersList();
        }
    }

    private void onClickButtonAdd(User user){
        User you = UserFacade.getInstance().getConnectedUser();
        Notification notification = new Notification(user,you,you.getPseudo() + " vous demande en ami(e)",true, NotificationCategory.createNotification("friendInvitation"),you.getId());
        NotificationFacade.getInstance().createNotification(notification);
    }


    public void onClickButtonToHomePage(ActionEvent event){
        SceneController.getInstance().switchToHomePage(event);
    }
}
