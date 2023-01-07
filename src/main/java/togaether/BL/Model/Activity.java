package togaether.BL.Model;

import java.util.Date;

public class Activity {

    private int activity_id;
    private int travelAssociated_id;
    private String name_activity;
    private String description_activity;
    private String address_activity;
    private Date date_start;
    private Date date_end;
    private double price_activity;

    /**
     * Constructor if you want to create Activity
     * @param activity_id
     * @param travelAssociated_id
     * @param name_activity
     * @param description_activity
     * @param address_activity
     * @param date_start
     * @param date_end
     * @param price_activity
     */
    public Activity(int activity_id, int travelAssociated_id, String name_activity, String description_activity, String address_activity, Date date_start, Date date_end, double price_activity) {
        this.activity_id = activity_id;
        this.travelAssociated_id = travelAssociated_id;
        this.name_activity = name_activity;
        this.description_activity = description_activity;
        this.address_activity = address_activity;
        this.date_start = date_start;
        this.date_end = date_end;
        this.price_activity = price_activity;
    }

    /**
     * Constructor if you insert data into DB (so IdActivity is automatically set)
     * @param travelAssociated_id
     * @param name_activity
     * @param description_activity
     * @param address_activity
     * @param date_start
     * @param date_end
     * @param price_activity
     */
    public Activity(int travelAssociated_id, String name_activity, String description_activity, String address_activity, Date date_start, Date date_end, double price_activity) {
        this.travelAssociated_id = travelAssociated_id;
        this.name_activity = name_activity;
        this.description_activity = description_activity;
        this.address_activity = address_activity;
        this.date_start = date_start;
        this.date_end = date_end;
        this.price_activity = price_activity;
    }

    /**
     * Constructor if you update data into DB (so IdActivity is automatically set, travelAssociated_id too)
     * @param name_activity
     * @param description_activity
     * @param address_activity
     * @param date_start
     * @param date_end
     * @param price_activity
     */
    public Activity( String name_activity, String description_activity, String address_activity, Date date_start, Date date_end, double price_activity) {
        this.name_activity = name_activity;
        this.description_activity = description_activity;
        this.address_activity = address_activity;
        this.date_start = date_start;
        this.date_end = date_end;
        this.price_activity = price_activity;
    }



    public Activity(int activity_id) {
        this.activity_id = activity_id;
    }
    // GETTER AND SETTER

    // IdActivity
    public int getIdActivity() {
        return activity_id;
    }

    public void setIdActivity(int activity_id) {
        this.activity_id = activity_id;
    }

    // TravelAssociated_id
    public int getTravelAssociated_id() {
        return travelAssociated_id;
    }

    public void setTravelAssociated_id(int travelAssociated_id) {
        this.travelAssociated_id = travelAssociated_id;
    }

    // NameActivity
    public String getNameActivity() {
        return name_activity;
    }

    public void setNameActivity(String name_activity) {
        this.name_activity = name_activity;
    }

    // DescriptionActivity

    public String getDescriptionActivity() {
        return description_activity;
    }

    public void setDescriptionActivity(String description_activity) {
        this.description_activity = description_activity;
    }

    // AddressActivity

    public String getAddressActivity() {
        return address_activity;
    }

    public void setAddressActivity(String address_activity) {
        this.address_activity = address_activity;
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

    // PriceActivity

    public double getPriceActivity() {
        return price_activity;
    }

    public void setPriceActivity(double price_activity) {
        this.price_activity = price_activity;
    }

    @Override
    public String toString() {
        return "Activity{" + "activity_id=" + activity_id + ", travelAssociated_id=" + travelAssociated_id + ", name_activity=" + name_activity + ", description_activity=" + description_activity + ", address_activity=" + address_activity + ", date_start=" + date_start + ", date_end=" + date_end + ", price_activity=" + price_activity + '}';
    }
}
