package togaether.BL.Model;

public class NotificationCategory {
    private int idCatNotification;
    private String nameCatNotification;

    private NotificationCategory(int idCatNotification, String nameCatNotification) {
        this.idCatNotification = idCatNotification;
        this.nameCatNotification = nameCatNotification;
    }

    public static NotificationCategory createNotification(String type){
        switch(type){
            case "friendInvitation":
                return new NotificationCategory(1,"friendInvitation");
            case "travelInvitation":
                return new NotificationCategory(2,"travelInvitation");
            case "trophyObtained":
                return new NotificationCategory(3,"trophyObtained");
            case "serverNotification":
                return new NotificationCategory(4,"serverNotification");
            default:
                return null;
        }
    }

    public int getIdCatNotification() {
        return idCatNotification;
    }

    public String getNameCatNotification() {
        return nameCatNotification;
    }

    public void setNameCatNotification(String nameCatNotification) {
        this.nameCatNotification = nameCatNotification;
    }
}
