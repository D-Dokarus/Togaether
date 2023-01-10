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
import togaether.BL.Facade.DocumentFacade;
import togaether.BL.Facade.TravelFacade;
import togaether.BL.Model.Document;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.UI.SceneController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentController {

    //Button
    @FXML
    private Button returnButton;
    @FXML
    private Button unusable;
    @FXML
    private Button addDocument;

    //Label
    @FXML
    private ListView DocumentList;
    @FXML
    private Label labelError;

    //TextField

    @FXML
    private TextField searchDocument;

    //ListViews
    @FXML
    private ListView documentList;

    Document documentToDL = null;
    //String which is path to Download folder
    String homeEn = System.getProperty("user.home") + "/Downloads/";
    String homeFr = System.getProperty("user.home") + "/Téléchargements/";
    String language = System.getProperty("user.language");

    @FXML
    protected void initialize() {
        this.loadDocuments();
    }

    private void loadDocuments() {
        this.unusable.setDisable(true);
        DocumentFacade documentFacade = DocumentFacade.getInstance();
        ArrayList<Document> documents = (ArrayList<Document>) documentFacade.findDocumentsByTravelId(TravelFacade.getInstance().getTravel().getIdTravel());

        ObservableList<Document> observableDocuments = FXCollections.observableArrayList();
        for (Document document : documents) {
            observableDocuments.add(document);
        }

        // We need to create a new CellFactory so we can display our layout for each individual notification
        this.documentList.setCellFactory((Callback<ListView<Document>, ListCell<Document>>) param -> {
            return new ListCell<Document>() {
                @Override
                protected void updateItem(Document document, boolean empty) {
                    super.updateItem(document, empty);

                    //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                    if (document == null || empty) {
                        setText(null);
                    } else {
                        // Here we can build the layout we want for each ListCell.
                        HBox root = new HBox(10);
                        root.setAlignment(Pos.CENTER_LEFT);
                        root.setPadding(new Insets(5, 10, 5, 10));

                        // Within the root, we'll show the username on the left and our two buttons to the right
                        root.getChildren().add(new Label(document.getName()));

                        // Add another Region here to expand, pushing the buttons to the right
                        Region region = new Region();
                        HBox.setHgrow(region, Priority.ALWAYS);
                        root.getChildren().add(region);

                        //BUTTON DLDocument
                        Button btnGoDocument = new Button("Télécharger");
                        btnGoDocument.setOnAction(event -> {
                            DocumentFacade.getInstance().setDocument(document);
                            documentToDL = DocumentFacade.getInstance().findDocumentById(document.getIdDocument());
                            try {
                                downloadDocument(documentToDL);
                                System.out.println("Document téléchargé");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            //SceneController.getInstance().switchToUpdateDocument(event);
                        });
                        //BUTTON DELETEDocument
                        Button btnDeleteDocument = new Button("Supprimer");
                        btnDeleteDocument.setOnAction(event -> {
                            try {
                                DocumentFacade.getInstance().setDocument(document);
                                DocumentFacade.getInstance().deleteDocument(document);
                                loadDocuments();
                                System.out.println("Document supprimé");
                            } catch (DBNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        root.getChildren().addAll(btnGoDocument, btnDeleteDocument);

                        // Finally, set our cell to display the root HBox
                        setText(null);
                        setGraphic(root);
                    }

                }
            };

        });

        // Set our users to display in the ListView
        documentList.setItems(observableDocuments);
    }

    public void onSearchDocumensButtonClicked(ActionEvent event) {
        // FAIRE LA RECHERCHE
        // Si barre de recherche vide, afficher tous les documents
        // Sinon, afficher les documents correspondant à la recherche
        if (searchDocument.getText().equals("")) {
            this.loadDocuments();
        } else {
            this.unusable.setDisable(false);
            DocumentFacade documentFacade = DocumentFacade.getInstance();
            ArrayList<Document> documents = (ArrayList<Document>) documentFacade.findDocumentsByTravelId(TravelFacade.getInstance().getTravel().getIdTravel());

            ObservableList<Document> observableDocumentsSearched = FXCollections.observableArrayList();
            for (Document doc : documents) {
                if (doc.getName().contains(searchDocument.getText())) {
                    observableDocumentsSearched.add(doc);
                }
            }

            // We need to create a new CellFactory so we can display our layout for each individual notification
            this.documentList.setCellFactory((Callback<ListView<Document>, ListCell<Document>>) param -> {
                return new ListCell<Document>() {
                    @Override
                    protected void updateItem(Document document, boolean empty) {
                        super.updateItem(document, empty);

                        //TO DO ajouter un && avec un string de recherche de nom (si pas "")
                        if (document == null || empty) {
                            setText(null);
                        } else {
                            // Here we can build the layout we want for each ListCell.
                            HBox root = new HBox(10);
                            root.setAlignment(Pos.CENTER_LEFT);
                            root.setPadding(new Insets(5, 10, 5, 10));

                            // Within the root, we'll show the username on the left and our two buttons to the right
                            root.getChildren().add(new Label(document.getName()));

                            // Add another Region here to expand, pushing the buttons to the right
                            Region region = new Region();
                            HBox.setHgrow(region, Priority.ALWAYS);
                            root.getChildren().add(region);

                            //BUTTON DLDocument
                            Button btnGoDocument = new Button("Télécharger");
                            btnGoDocument.setOnAction(event -> {
                                DocumentFacade.getInstance().setDocument(document);
                                documentToDL = DocumentFacade.getInstance().findDocumentById(document.getIdDocument());
                                try {
                                    downloadDocument(documentToDL);
                                    System.out.println("Document téléchargé");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                //SceneController.getInstance().switchToUpdateDocument(event);
                            });
                            //BUTTON DELETEDocument
                            Button btnDeleteDocument = new Button("Supprimer");
                            btnDeleteDocument.setOnAction(event -> {
                                try {
                                    DocumentFacade.getInstance().setDocument(document);
                                    DocumentFacade.getInstance().deleteDocument(document);
                                    loadDocuments();
                                    System.out.println("Document supprimé");
                                } catch (DBNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            root.getChildren().addAll(btnGoDocument, btnDeleteDocument);

                            // Finally, set our cell to display the root HBox
                            setText(null);
                            setGraphic(root);
                        }

                    }
                };

            });

            // Set our users to display in the ListView
            documentList.setItems(observableDocumentsSearched);
        }
    }

    public void error(Exception e) {
        this.labelError.setText("Problème lors du chargement de la page, veuillez réessayer plus tard...");
    }

    public void onReturnButtonClicked(ActionEvent actionEvent) {
        SceneController.getInstance().switchToTravel(actionEvent);
    }

    public void onAddDocumentButtonClicked(ActionEvent actionEvent) {
        SceneController.getInstance().switchToCreateDocument(actionEvent);
    }


    public void downloadDocument(Document document) throws IOException {
        String sep = File.separator;
        String path = "";
        path = System.getProperty("user.dir") + sep + "Documents" + sep;
        try {
            FileOutputStream fos;
            fos = new FileOutputStream(path+document.getName());
            fos.write(document.getFile());
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

