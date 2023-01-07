package togaether.BL.Model;

import java.util.Date;

public class Itinerary {
    private int itinerary_id;
    private int travel;
    private String name;
    private Date dateItinerary;
    private int index;
    private int category;

    public Itinerary(int itinerary_id, int travel, String name, Date dateItinerary, int index, int category) {
        this.itinerary_id = itinerary_id;
        this.travel = travel;
        this.name = name;
        this.dateItinerary = dateItinerary;
        this.index = index;
        this.category = category;
    }

    public int getItinerary_id() {
        return itinerary_id;
    }

    public void setItinerary_id(int itinerary_id) {
        this.itinerary_id = itinerary_id;
    }

    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateItinerary() {
        return dateItinerary;
    }

    public void setDateItinerary(Date dateItinerary) {
        this.dateItinerary = dateItinerary;
    }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "itinerary_id=" + itinerary_id +
                ", travel=" + travel +
                ", name='" + name + '\'' +
                ", dateItinerary=" + dateItinerary +
                ", index=" + index +
                ", category=" + category +
                '}';
    }
}
