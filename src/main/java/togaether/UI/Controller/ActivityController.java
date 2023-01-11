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
import togaether.BL.Facade.ActivityFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Activity;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.UI.SceneController;

import java.util.ArrayList;

public class ActivityController {

    //Button
    @FXML
    private Button returnButton;
    @FXML
    private Button unusable;
    @FXML
    private Button addActivity;

    //Label
    @FXML
    private ListView activityList;
    @FXML
    private Label labelError;

    //TextField

    @FXML
    private TextField searchActivity;

    @FXML
    protected void initialize() {
        this.loadActivities();
    }

    private void loadActivities() {
        this.unusable.setDisable(true);
        ActivityFacade activityFacade = ActivityFacade.getInstance();
        ArrayList<Activity> activities = (ArrayList<Activity>) activityFacade.findActivitiesByTravelId(TravelFacade.getInstance().getTravel().getIdTravel());

        ObservableList<Activity> observableActivities = FXCollections.observableArrayList();
        for (Activity activity : activities) {
            observableActivities.add(activity);
        }

        // We need to create a new CellFactory so we can display our layout for each individual notification
        this.activityList.setCellFactory((Callback<ListView<Activity>, ListCell<Activity>>) param -> {
            return new ListCell<Activity>() {
                @Override
                protected void updateItem(Activity activity, boolean empty) {
                    super.updateItem(activity, empty);

                    //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                    if (activity == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(activity.getNameActivity()));
                        if(activity.getDateStart() != null)
                            root.getChildren().add(new Label(activity.getDateStart().toString()));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON GOActivity
                        Button btnGoActivity = new Button("Voir");
                        btnGoActivity.setOnAction(event -> {
                            ActivityFacade.getInstance().setActivity(activity);
                            SceneController.getInstance().switchToUpdateActivity(event);
                        });
                        //BUTTON DELETEActivity
                        Button btnDeleteActivity = new Button("Supprimer");
                        btnDeleteActivity.setOnAction(event -> {
                            try {
                                ActivityFacade.getInstance().setActivity(activity);
                                ActivityFacade.getInstance().deleteActivity(activity);
                                loadActivities();
                                System.out.println("Activité supprimée");
                            } catch (DBNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        root.getChildren().addAll(btnGoActivity, btnDeleteActivity);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });

        // Set our users to display in the ListView
        activityList.setItems(observableActivities);
    }

    //public void onCreateActivityButtonClicked(ActionEvent event) { SceneController.getInstance().switchToCreateActivity(event);}
    public void onSearchActivityButtonClicked(ActionEvent event) {
        // FAIRE LA RECHERCHE
        // Si barre de recherche vide, afficher toutes les activités
        // Sinon, afficher les activités correspondant à la recherche
        if (searchActivity.getText().equals("")) {
            this.loadActivities();
        } else {
            this.unusable.setDisable(false);
            ActivityFacade activityFacade = ActivityFacade.getInstance();
            ArrayList<Activity> activities = (ArrayList<Activity>) activityFacade.findActivitiesByTravelId(TravelFacade.getInstance().getTravel().getIdTravel());

            ObservableList<Activity> observableActivitiesSearched = FXCollections.observableArrayList();
            for (Activity activity : activities) {
                if (activity.getNameActivity().contains(searchActivity.getText())) {
                    observableActivitiesSearched.add(activity);
                }
            }

            // We need to create a new CellFactory so we can display our layout for each individual notification
            this.activityList.setCellFactory((Callback<ListView<Activity>, ListCell<Activity>>) param -> {
                return new ListCell<Activity>() {
                    @Override
                    protected void updateItem(Activity activity, boolean empty) {
                        super.updateItem(activity, empty);

                        //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                        if (activity == null || empty) {
                            setText(null);
                        } else {
                            // Here we can build the layout we want for each ListCell.
                            HBox root = new HBox(10);
                            root.setAlignment(Pos.CENTER_LEFT);
                            root.setPadding(new Insets(5, 10, 5, 10));

                            // Within the root, we'll show the username on the left and our two buttons to the right
                            root.getChildren().add(new Label(activity.getNameActivity()));
                            if(activity.getDateStart() != null)
                                root.getChildren().add(new Label(activity.getDateStart().toString()));

                            // Add another Region here to expand, pushing the buttons to the right
                            Region region = new Region();
                            HBox.setHgrow(region, Priority.ALWAYS);
                            root.getChildren().add(region);

                            //BUTTON GOActivity
                            Button btnGoActivity = new Button("Voir");
                            btnGoActivity.setOnAction(event -> {
                                ActivityFacade.getInstance().setActivity(activity);
                                SceneController.getInstance().switchToUpdateActivity(event);
                            });
                            //BUTTON DELETEActivity
                            Button btnDeleteActivity = new Button("Supprimer");
                            btnDeleteActivity.setOnAction(event -> {
                                try {
                                    ActivityFacade.getInstance().setActivity(activity);
                                    ActivityFacade.getInstance().deleteActivity(activity);
                                    loadActivities();
                                    System.out.println("Activité supprimée");
                                } catch (DBNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            root.getChildren().addAll(btnGoActivity, btnDeleteActivity);

                            // Finally, set our cell to display the root HBox
                            setText(null);
                            setGraphic(root);
                        }

                    }
                };

            });

            // Set our users to display in the ListView
            activityList.setItems(observableActivitiesSearched);
        }
    }

    public void error(Exception e) {
        this.labelError.setText("Problème lors du chargement de la page, veuillez réessayer plus tard...");
    }

    public void onReturnButtonClicked(ActionEvent actionEvent) {
        SceneController.getInstance().switchToTravel(actionEvent);
    }

    public void onAddActivityButtonClicked(ActionEvent actionEvent) {

        SceneController.getInstance().switchToCreateActivity(actionEvent);
    }
}
