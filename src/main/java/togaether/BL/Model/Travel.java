package togaether.BL.Model;

import java.util.Date;

public class Travel {

    private int travel_id;
    private User owner ;
    private String name_travel;
    private String description_travel;
    private Date date_start;
    private Date date_end;
    private boolean is_archive;

    /**
     * Constructor if you want to create Travel
     * @param travel_id
     * @param owner
     * @param name_travel
     * @param description_travel
     * @param date_start
     * @param date_end
     * @param is_archive
     */
    public Travel(int travel_id, User owner, String name_travel, String description_travel, Date date_start, Date date_end, boolean is_archive) {
        this.travel_id = travel_id;
        this.owner = owner;
        this.name_travel = name_travel;
        this.description_travel = description_travel;
        this.date_start = date_start;
        this.date_end = date_end;
        this.is_archive = is_archive;
    }

    /**
     * Constructor if you insert data into DB (so IdTravel is automatically set)
     * @param owner
     * @param name_travel
     * @param description_travel
     * @param date_start
     * @param date_end
     * @param is_archive
     */
    public Travel(User owner, String name_travel, String description_travel, Date date_start, Date date_end, boolean is_archive) {
        this.owner = owner;
        this.name_travel = name_travel;
        this.description_travel = description_travel;
        this.date_start = date_start;
        this.date_end = date_end;
        this.is_archive = is_archive;
    }

    // GETTER AND SETTER

    // IdTravel
    public int getIdTravel() {
        return travel_id;
    }

    public void setIdTravel(int travel_id) {
        this.travel_id = travel_id;
    }

    // Owner
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // NameTravel
    public String getNameTravel() {
        return name_travel;
    }

    public void setNameTravel(String name_travel) {
        this.name_travel = name_travel;
    }

    // DescriptionTravel
    public String getDescriptionTravel() {
        return description_travel;
    }

    public void setDescriptionTravel(String description_travel) {
        this.description_travel = description_travel;
    }

    // DateStart
    public Date getDateStart() {
        return date_start;
    }

    public void setDateStart(Date date_start) {
        this.date_start = date_start;
    }

    // DateEnd
    public Date getDateEnd() {
        return date_end;
    }

    public void setDateEnd(Date date_end) {
        this.date_end = date_end;
    }

    // IsArchive
    public boolean isArchive() {
        return is_archive;
    }

    public void setArchive(boolean is_archive) {
        this.is_archive = is_archive;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "idTravel=" + travel_id +
                ", owner=" + owner +
                ", nameTravel='" + name_travel + '\'' +
                ", descriptionTravel='" + description_travel + '\'' +
                ", dateStart=" + date_start +
                ", dateEnd=" + date_end +
                ", isArchive=" + is_archive +
                '}';
    }
}
