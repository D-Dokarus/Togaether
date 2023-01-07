package togaether.BL.Model;

public class TransportCategory {
    private int catTransport_id;
    private String nameTransportCat;

    /**
     * Constructor if you want to create TransportCategory
     * @param catTransport_id
     * @param nameTransportCat
     */
    public TransportCategory(int catTransport_id, String nameTransportCat) {
        this.catTransport_id = catTransport_id;
        this.nameTransportCat = nameTransportCat;
    }

    public int getCatTransport_id() {
        return catTransport_id;
    }

    public void setCatTransport_id(int catTransport_id) {
        this.catTransport_id = catTransport_id;
    }

    public String getNameTransportCat() {
        return nameTransportCat;
    }

    public void setNameTransportCat(String nameTransportCat) {
        this.nameTransportCat = nameTransportCat;
    }

    @Override
    public String toString() {
        return "TransportCategory{" +
                "catTransport_id=" + catTransport_id +
                ", nameTransportCat='" + nameTransportCat + '\'' +
                '}';
    }
}
