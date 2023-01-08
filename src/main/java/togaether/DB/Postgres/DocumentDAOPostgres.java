package togaether.DB.Postgres;

import togaether.BL.Model.Document;
import togaether.DB.DocumentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAOPostgres implements DocumentDAO {

    PostgresFactory postgres;

    public DocumentDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }

    @Override
    public void createDocument(Document document) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "INSERT INTO document (travel_id, document_name, document_data, document_path) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, document.getIdTravel());
            preparedStatement.setString(2, document.getName());
            preparedStatement.setBytes(3, document.getFile());
            preparedStatement.setString(4, document.getPath());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateDocument(Document document) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "UPDATE document SET name_document = ?, description_document = ?, address_document = ?, date_start = ?, date_end = ?, price_document = ? WHERE document_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {

                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteDocumentById(int document_id) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "DELETE FROM document WHERE document_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, document_id);
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteDocument(Document document) throws SQLException {
        try (Connection connection = this.postgres.getConnection()) {
            String query = "DELETE FROM document WHERE document_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, document.getIdDocument());
                statement.executeUpdate();
            }
        }
    }


    @Override
    public List<Document> findAllDocuments() throws SQLException {
        List<Document> activities = new ArrayList<>();
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM document";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        activities.add(new Document(
                                resultSet.getInt("document_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("name_document"),
                                resultSet.getString("description_document")
                        ));
                    }
                }
            }
        }
        return activities;
    }

    @Override
    public List<Document> findAllDocumentsByTravelId(int travel_id) throws SQLException {
        List<Document> documents = new ArrayList<>();
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM document WHERE travel_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, travel_id);
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        documents.add(new Document(
                                resultSet.getInt("document_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("document_name"),
                                resultSet.getBytes("document_data")
                        ));
                    }
                }
            }
        }
        return documents;
    }

    @Override
    public Document findDocumentById(int document_id) throws SQLException {
        Document document = null;
        try (Connection connection = this.postgres.getConnection()) {
            String query = "SELECT * FROM document WHERE document_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, document_id);
                try (ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next()) {
                        document = new Document(
                                resultSet.getInt("document_id"),
                                resultSet.getInt("travel_id"),
                                resultSet.getString("document_name"),
                                resultSet.getBytes("document_data")
                        );
                    }
                }
            }
        }
        return document;
    }


}
