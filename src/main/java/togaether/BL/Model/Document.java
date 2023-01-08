package togaether.BL.Model;

public class Document {

    private int idDocument;
    private int idTravel;
    private String name;
    private String path;

    private byte[] file;

    public Document(int idDocument, int idTravel, String name, String path, byte[] file) {
        this.idDocument = idDocument;
        this.idTravel = idTravel;
        this.name = name;
        this.path = path;
        this.file = file;
    }

    // Document to Download
    public Document(int idDocument, int idTravel, String name, byte[] file) {
        this.idDocument = idDocument;
        this.idTravel = idTravel;
        this.name = name;
        this.file = file;
    }
    public Document(int document_id, int travel_id, String name_document, String description_document) {
        this.idDocument = document_id;
        this.idTravel = travel_id;
        this.name = name_document;
        this.path = description_document;
    }

    public Document(int idTravel, String name, byte[] file, String path) {
        this.idTravel = idTravel;
        this.name = name;
        this.path = path;
        this.file = file;
    }

    //Getters and Setters

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIdTravel() {
        return idTravel;
    }

    public void setIdTravel(int idTravel) {
        this.idTravel = idTravel;
    }

    public byte[] getFile() {
        return file;
    }
    
    public void setFile(byte[] file) {
        this.file = file;
    }
}
