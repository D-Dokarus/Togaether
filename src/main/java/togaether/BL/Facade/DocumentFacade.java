package togaether.BL.Facade;


import togaether.BL.Model.Document;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentFacade {

    private Document document = null;

    static private DocumentFacade instance = new DocumentFacade();

    public static DocumentFacade getInstance() {
        return instance;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Query the creation of the actual document in DB
     */
    public void createDocument(Document document) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        DocumentDAO documentDB = fact.getDocumentDAO();
        try {
            documentDB.createDocument(document);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the update of the actual document in DB
     */
    public void updateDocument(Document document) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        DocumentDAO documentDB = fact.getDocumentDAO();
        try {
            documentDB.updateDocument(document);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the deletion of the actual document in DB
     */
    public void deleteDocument(Document document) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        DocumentDAO documentDB = fact.getDocumentDAO();
        try {
            documentDB.deleteDocument(document);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the finding of the actual document in DB
     */
    public List<Document> findDocumentsByTravelId(int id) {
        ArrayList<Document> liste = new ArrayList<>();
        DocumentDAO documentDAO = AbstractFactory.createInstance().getDocumentDAO();
        try {
            liste = (ArrayList<Document>) documentDAO.findAllDocumentsByTravelId(id);
        } catch (SQLException e) {
        }
        return liste;
    }

    /**
     * Query the finding of the document by id in DB
     *
     * @param id
     */
    public Document findDocumentById(int id) {
        Document document = null;
        DocumentDAO documentDAO = AbstractFactory.createInstance().getDocumentDAO();
        try {
            document = documentDAO.findDocumentById(id);
        } catch (SQLException e) {
        }
        return document;

    }
}
