package togaether.BL.Model;

import java.util.Date;

public class Travel {

    private int idTravel;
    private User owner ;
    private String nameTravel;
    private String descriptionTravel;
    private Date dateStart;
    private Date dateEnd;
    private boolean isArchive;

    /**
     * Constructor if you want to create Travel
     * @param idTravel
     * @param owner
     * @param nameTravel
     * @param descriptionTravel
     * @param dateStart
     * @param dateEnd
     * @param isArchive
     */
    public Travel(int idTravel, User owner,String nameTravel, String descriptionTravel, Date dateStart, Date dateEnd, boolean isArchive) {
        this.idTravel = idTravel;
        this.owner = owner;
        this.nameTravel = nameTravel;
        this.descriptionTravel = descriptionTravel;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isArchive = isArchive;
    }

    /**
     * Constructor if you insert data into DB (so IdTravel is automatically set)
     * @param owner
     * @param nameTravel
     * @param descriptionTravel
     * @param dateStart
     * @param dateEnd
     * @param isArchive
     */
    public Travel(User owner, String nameTravel, String descriptionTravel, Date dateStart, Date dateEnd, boolean isArchive) {
        this.owner = owner;
        this.nameTravel = nameTravel;
        this.descriptionTravel = descriptionTravel;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isArchive = isArchive;
    }

    // GETTER AND SETTER

    // IdTravel
    public int getIdTravel() {
        return idTravel;
    }

    public void setIdTravel(int idTravel) {
        this.idTravel = idTravel;
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
        return nameTravel;
    }

    public void setNameTravel(String nameTravel) {
        this.nameTravel = nameTravel;
    }

    // DescriptionTravel
    public String getDescriptionTravel() {
        return descriptionTravel;
    }

    public void setDescriptionTravel(String descriptionTravel) {
        this.descriptionTravel = descriptionTravel;
    }

    // DateStart
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    // DateEnd
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    // IsArchive
    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "idTravel=" + idTravel +
                ", owner=" + owner +
                ", nameTravel='" + nameTravel + '\'' +
                ", descriptionTravel='" + descriptionTravel + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", isArchive=" + isArchive +
                '}';
    }
}
