package togaether.DB;

import togaether.BL.Model.Document;

import java.sql.SQLException;
import java.util.List;

public interface DocumentDAO {
    /**
     * Query the creation of the actual document in DB
     */
    void createDocument(Document document) throws SQLException;

    /**
     * Query the update of the actual activity in DB
     */
    void updateDocument(Document document) throws SQLException;

    /**
     * Query the deleting of the actual document in DB
     */
    void deleteDocument(Document document) throws SQLException;

    void deleteDocumentById(int id) throws SQLException;


    //Find all documents by travel id
    List<Document> findAllDocumentsByTravelId(int travelId) throws SQLException;

    //Find all documents
    List<Document> findAllDocuments() throws SQLException;

    Document findDocumentById(int document_id) throws SQLException;
}
