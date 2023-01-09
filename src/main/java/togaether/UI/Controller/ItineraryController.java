package togaether.UI.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import togaether.BL.Facade.ItineraryFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Itinerary;
import togaether.BL.Model.Travel;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.UI.SceneController;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;

public class ItineraryController {

    @FXML
    private Label labelError;
    @FXML
    private Label ItineraireLabel;
    @FXML
    private ListView ItineraryList;
    private TravelFacade travelFacade;
    private ItineraryFacade itineraryFacade;

    private List<Itinerary> itinerariesOfTravel;
    @FXML
    protected void initialize() {
        this.itineraryFacade = ItineraryFacade.getInstance();
        this.travelFacade = TravelFacade.getInstance();

        try {
            this.itinerariesOfTravel = (List<Itinerary>) this.itineraryFacade.findItineraries(travelFacade.getTravel().getIdTravel());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DBNotFoundException e) {
            this.labelError.setText("Problème lors du chargement des itinéraires de ce voyage, veuillez réessayer plus tard...");
            throw new RuntimeException(e);
        }

        if(this.itinerariesOfTravel.size() != 0){
            this.ItineraireLabel.setVisible(false);
        } else {
            this.ItineraireLabel.setVisible(true);
        }

        // TO DO
        // SI LIST VIDE LAISSER LA PHRASE, SINON L'ENLEVER
        ObservableList<Itinerary> observableItineraries = FXCollections.observableArrayList();
        for(Itinerary itinerary : itinerariesOfTravel){
            observableItineraries.add(itinerary);
        }
        this.ItineraryList.setCellFactory((Callback<ListView<Itinerary>, ListCell<Itinerary>>) param -> {
            return new ListCell<Itinerary>() {
                @Override
                protected void updateItem(Itinerary itinerary, boolean empty) {
                    super.updateItem(itinerary, empty);

                    //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                    if (itinerary == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(itinerary.getName()));
                        // root.getChildren().add(new Label(itinerary.getDescription()));
                        // root.getChildren().add(new Label(itinerary.getDateItinerary().toString()));
                        // root.getChildren().add(new Label(itinerary.getHour()));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON BEFORE ITINERARY
                        Button btnBeforeItinerary = new Button("⬆");
                        btnBeforeItinerary.setOnAction(event -> {
                            try {
                                LinkedList<Itinerary> linkedList = travelFacade.findItineraries(travelFacade.getTravel().getIdTravel());
                                int indexLinkedList = -1;
                                int i =0;
                                boolean found = false;
                                while (!found){
                                    if(linkedList.get(i).getItinerary_id() == itinerary.getItinerary_id()){
                                        indexLinkedList = i;
                                        found = true;
                                    }
                                    i++;
                                }
                                if(itinerary.getIndexBefore()!=-1){
                                    if(itinerary.getIndexAfter()==-1 && linkedList.get(indexLinkedList-1).getIndexBefore()==-1){ // 2 Itineraires seulement
                                        itineraryFacade.switchIndexFor2ItinerariesById(itinerary.getIndexBefore(), itinerary.getItinerary_id());
                                    } else if(linkedList.get(indexLinkedList-1).getIndexBefore()==-1){ // Deuxième itineraire de la liste avec le premier (liste.size() > 2)
                                        itineraryFacade.switchIndexBeforeFor3ItinerariesById(itinerary.getIndexBefore(), itinerary.getItinerary_id(), itinerary.getIndexAfter());
                                    } else if(itinerary.getIndexAfter()!=-1){ // Pour A B C D qui se suivent pour changement B-C avec itinerary = C
                                        itineraryFacade.switchIndexFor4ItinerariesById(linkedList.get(indexLinkedList-2).getItinerary_id(), itinerary.getIndexBefore(), itinerary.getItinerary_id(),itinerary.getIndexAfter());
                                    } else { // Dernier itineraire de la liste
                                        itineraryFacade.switchIndexAfterFor3ItinerariesById(linkedList.get(indexLinkedList-1).getIndexBefore(), itinerary.getIndexBefore(), itinerary.getItinerary_id());
                                    }
                                }
                                SceneController.getInstance().switchToItinerary(event);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        //BUTTON AFTER ITINERARY
                        Button btnAfterItinerary = new Button("⬇");
                        btnAfterItinerary.setOnAction(event -> {
                            try {
                                LinkedList<Itinerary> linkedList = travelFacade.findItineraries(travelFacade.getTravel().getIdTravel());
                                int indexLinkedList = -1;
                                int i =0;
                                boolean found = false;
                                while (!found){
                                    if(linkedList.get(i).getItinerary_id() == itinerary.getItinerary_id()){
                                        indexLinkedList = i;
                                        found = true;
                                    }
                                    i++;
                                }
                                if(itinerary.getIndexAfter()!=-1){
                                    if(itinerary.getIndexBefore()==-1 && linkedList.get(indexLinkedList+1).getIndexAfter()==-1){ // 2 itineraire seulement
                                        itineraryFacade.switchIndexFor2ItinerariesById(itinerary.getItinerary_id(), itinerary.getIndexAfter());
                                    } else if(linkedList.get(indexLinkedList+1).getIndexAfter()==-1){ // Avant dernier itineraire de la liste avec le dernier (liste.size() > 2)
                                        itineraryFacade.switchIndexAfterFor3ItinerariesById(itinerary.getIndexBefore(), itinerary.getItinerary_id(), itinerary.getIndexAfter());
                                    } else if(itinerary.getIndexAfter()!=-1){ // Pour A B C D qui se suivent pour changement B-C avec itinerary = B
                                        itineraryFacade.switchIndexFor4ItinerariesById(itinerary.getIndexBefore(), itinerary.getItinerary_id(), itinerary.getIndexAfter(),linkedList.get(indexLinkedList+2).getItinerary_id());
                                    } else { // Premier itineraire de la liste
                                        System.out.println(itinerary.getItinerary_id());
                                        System.out.println(itinerary.getIndexAfter());
                                        System.out.println(linkedList.get(indexLinkedList+1).getIndexAfter());
                                        itineraryFacade.switchIndexBeforeFor3ItinerariesById(itinerary.getItinerary_id(), itinerary.getIndexAfter(), linkedList.get(indexLinkedList+1).getIndexAfter());
                                    }
                                }
                                SceneController.getInstance().switchToItinerary(event);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        //BUTTON DELETE ITINERARY
                        Button btnDeleteItinerary = new Button("Supprimer");
                        btnDeleteItinerary.setOnAction(event -> {
                            try {
                                ItineraryFacade.getInstance().deleteItineraryById(itinerary.getItinerary_id());
                                if(itinerary.getIndexBefore()==-1 && itinerary.getIndexAfter()!=-1){
                                    itineraryFacade.updateIndexBeforeItineraryById(itinerary.getIndexAfter(), -1);
                                } else if(itinerary.getIndexBefore()!=-1 && itinerary.getIndexAfter()==-1){
                                    itineraryFacade.updateIndexAfterItineraryById(itinerary.getIndexBefore(), -1);
                                } else if(itinerary.getIndexBefore()!=-1 && itinerary.getIndexAfter()!=-1){
                                    itineraryFacade.updateIndexAfterItineraryById(itinerary.getIndexBefore(), itinerary.getIndexAfter());
                                    itineraryFacade.updateIndexBeforeItineraryById(itinerary.getIndexAfter(), itinerary.getIndexBefore());
                                }
                                SceneController.getInstance().switchToItinerary(event);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (DBNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        root.getChildren().addAll(btnBeforeItinerary, btnAfterItinerary, btnDeleteItinerary);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });
        // Set our users to display in the ListView
        this.ItineraryList.setItems(observableItineraries);
    }

    public void onReturnButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToTravel(event);
    }

    public void onCreateItineraryButtonClicked(ActionEvent event) {
        SceneController.getInstance().switchToCreateItinerary(event);
    }

    public void onUpdateItineraryClicked(ActionEvent event) {
        SceneController.getInstance().switchToCreateItinerary(event);
    }

}
