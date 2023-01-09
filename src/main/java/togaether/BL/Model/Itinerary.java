package togaether.BL.Model;

import java.util.Date;

public class Itinerary {
    private int itinerary_id;
    private int travel;
    private String name;
    private Date dateItinerary;
    private int indexBefore;
    private int indexAfter;
    private int category;
    private String description;
    private String hour;

    public Itinerary(int itinerary_id, int travel, String name, Date dateItinerary, int indexBefore, int category, String description, String hour, int indexAfter) {
        this.itinerary_id = itinerary_id;
        this.travel = travel;
        this.name = name;
        this.dateItinerary = dateItinerary;
        this.indexBefore = indexBefore;
        this.category = category;
        this.description = description;
        this.hour = hour;
        this.indexAfter = indexAfter;
    }

    public Itinerary(int travel, String name, Date dateItinerary, int indexBefore, int category, String description, String hour, int indexAfter) {
        this.travel = travel;
        this.name = name;
        this.dateItinerary = dateItinerary;
        this.indexBefore = indexBefore;
        this.category = category;
        this.description = description;
        this.hour = hour;
        this.indexAfter = indexAfter;
    }

    public int getItinerary_id() {
        return itinerary_id;
    }

    // public void setItinerary_id(int itinerary_id) {
    //     this.itinerary_id = itinerary_id;
    // }

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

    public int getIndexBefore() {
        return indexBefore;
    }

    // public void setIndexBefore(int indexBefore) {
    //     this.indexBefore = indexBefore;
    // }

    public int getCategory() {
        return category;
    }

    // public void setCategory(int category) {
    //     this.category = category;
    // }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getIndexAfter() {
        return indexAfter;
    }

    // public void setIndexAfter(int indexAfter) {
    //     this.indexAfter = indexAfter;
    // }


}
