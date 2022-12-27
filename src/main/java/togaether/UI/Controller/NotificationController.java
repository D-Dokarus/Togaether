package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import togaether.BL.Facade.NotificationFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Notification;
import togaether.BL.Model.NotificationCategory;
import togaether.BL.Model.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationController {
    @FXML
    private Label label;
    @FXML
    private ListView listView;
    @FXML
    private Button button;

    /**
     * List of notifications fetched from DB
     */
    List<Notification> notifications = new ArrayList<>();
    /**
     * List of notifications display to the user
     */
    ObservableList<Notification> observableNotifications = FXCollections.observableArrayList();

    /**
     * The connected user
     */
    User user;
    /**
     * Number of Notifications in the DB
     * Refreshed each time you fetch new notifications OR open the NotificationFrame
     */
    int count;
    /**
     * NotificationId used to fetch newest notifications
     */
    int maxNotifId = 0;
    /**
     * Min notifications amount before fetching new notifications.
     * Also used to limit the number of new notifications fetch and added to the listView.
     */
    int limit = 5;

    /**
     * Method that is used just after creation of the class instance
     */
    @FXML
    protected void initialize(){
        //Code à utiliser une fois le projet fonctionnel
        //this.user = UserFacade.getConnectedUser();
        this.user = new User(4,"Maxime","maxime@gmail.com","password");
        this.count = NotificationFacade.getInstance().getNbNotificationsByUserId(this.user);
        updateLabel();
        updateList();
        initializeListView();
    }

    /**
     * Initialize the ListView
     * //Used each time to refresh the view of the ListView
     */
    public void initializeListView(){
        for(Notification notification : notifications){
            observableNotifications.add(notification);
        }
        // We need to create a new CellFactory so we can display our layout for each individual notification
        listView.setCellFactory((Callback<ListView<Notification>, ListCell<Notification>>) param -> {
            return new ListCell<Notification>() {
                @Override
                protected void updateItem(Notification notification, boolean empty) {
                    super.updateItem(notification, empty);

                    if (notification == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(notification.getContentNotif()));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON ACCEPT
                        Button btnAccept = new Button("V");
                        btnAccept.setOnAction(event -> {

                            acceptNotification(notification);
                            updateList();
                        });
                        //BUTTON REMOVE
                        Button btnRemove = new Button("X");
                        btnRemove.setOnAction(event -> {

                            // Code to remove notification
                            refuseNotification(notification);
                            updateList();

                        });
                        root.getChildren().addAll(btnAccept, btnRemove);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });

        // Set our users to display in the ListView
        listView.setItems(observableNotifications);

    }

    /**
     * Fetch new Notifications of connected User if he does have notifications
     */
    public void updateList(){
        if(notifications.size() < this.count & notifications.size() < this.limit){
            this.count = NotificationFacade.getInstance().getNbNotificationsByUserId(user);
            List<Notification> temp = NotificationFacade.getInstance().getSpecificAmountOfNotificationsByUserIdAndStartingId(user,limit,maxNotifId);
            int max = 0;
            boolean firstTime = notifications.isEmpty(); //Because this method is used both to initialize and update
            for(Notification notification : temp){
                notifications.add(notification);
                if(!firstTime){
                    observableNotifications.add(notification);
                }
                if(notification.getIdNotif() > max){
                    max = notification.getIdNotif();
                }
            }
            maxNotifId = max;
        }
    }

    /**
     * Update the Label of Text
     */
    public void updateLabel(){
        if(this.count >=0 ){
            label.setText("Vous avez " + count + " notifications !");
        }else{
            label.setText("Une erreur est apparue lors du comptage des notifications");
        }
    }

    /**
     * Remove the notification from both list and update the view
     * @param notification
     */
    public void removeNotificationFromList(Notification notification){
        count = count -1;
        updateLabel();
        observableNotifications.remove(notification);
        notifications.remove(notification);
        listView.getItems().removeAll(observableNotifications);
        initializeListView();
    }

    /**
     * If the button accept is clicked :
     * - We make NotificationFacade handle it
     * - We remove the notification from listView
     * @param notification
     */
    public void acceptNotification(Notification notification){
        NotificationFacade.getInstance().acceptNotification(notification);
        removeNotificationFromList(notification);
    }

    /**
     * If the button refuse is clicked :
     * - We make NotificationFacade handle it
     * - We remove the notification from listView
     * @param notification
     */
    public void refuseNotification(Notification notification){
        NotificationFacade.getInstance().refuseNotification(notification);
        removeNotificationFromList(notification);
    }
}
