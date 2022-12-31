package togaether.BL.Model;

public class Notification {
    private int id;
    private User to ;
    private User from;
    private String contentNotif;
    private boolean accept;
    private NotificationCategory catNotification;
    private int specialId;

    /**
     * Constructor if you want to create Notification
     * @param id
     * @param to
     * @param from
     * @param contentNotif
     * @param accept
     * @param catNotification
     * @param specialId
     */
    public Notification(int id, User to, User from, String contentNotif, boolean accept, NotificationCategory catNotification, int specialId) {
        this.id = id;
        this.to = to;
        this.from = from;
        this.contentNotif = contentNotif;
        this.accept = accept;
        this.catNotification = catNotification;
        this.specialId = specialId;
    }

    /**
     * Constructor if you insert data into DB (so IdNotif is automatically set)
     *
     * @param to
     * @param from
     * @param contentNotif
     * @param accept
     * @param catNotification
     * @param specialId
     */
    public Notification(User to, User from, String contentNotif, boolean accept, NotificationCategory catNotification, int specialId) {
        this.to = to;
        this.from = from;
        this.contentNotif = contentNotif;
        this.accept = accept;
        this.catNotification = catNotification;
        this.specialId = specialId;
    }

    /**
     * Constructor if you insert data into DB (so IdNotif is automatically set)
     * And you don't know the sender
     * @param to
     * @param contentNotif
     * @param accept
     * @param catNotification
     * @param specialId
     */
    public Notification(User to, String contentNotif, boolean accept, NotificationCategory catNotification, int specialId) {
        this.to = to;
        this.contentNotif = contentNotif;
        this.accept = accept;
        this.catNotification = catNotification;
        this.specialId = specialId;
    }

    /**
     * Constructor if you insert data into DB (so IdNotif is automatically set)
     * And there is no specialId
     * @param to
     * @param from
     * @param contentNotif
     * @param accept
     * @param catNotification
     */
    public Notification(User to, User from, String contentNotif, boolean accept, NotificationCategory catNotification) {
        this.to = to;
        this.from = from;
        this.contentNotif = contentNotif;
        this.accept = accept;
        this.catNotification = catNotification;
    }

    /**
     * Constructor if you insert data into DB (so IdNotif is automatically set)
     * And you don't know the sender
     * And there is no specialId
     * @param to
     * @param contentNotif
     * @param accept
     * @param catNotification
     */
    public Notification(User to, String contentNotif, boolean accept, NotificationCategory catNotification) {
        this.to = to;
        this.contentNotif = contentNotif;
        this.accept = accept;
        this.catNotification = catNotification;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }
    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getContentNotif() {
        return contentNotif;
    }

    public void setContentNotif(String contentNotif) {
        this.contentNotif = contentNotif;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public NotificationCategory getCatNotification() {
        return catNotification;
    }

    public void setCatNotification(NotificationCategory catNotification) {
        this.catNotification = catNotification;
    }

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }


    /**
     * Return a string that containts the value of each fields
     * @return
     */
    @Override
    public String toString() {
        String res = "Les valeurs de la notification sont : \n\n";
        res += "La valeur du champ " + "Id" + " est : " + this.getId()+ "\n";
        res += "La valeur du champ " + "To" + " est : " + this.getTo()+ "\n";
        res += "La valeur du champ " + "From" + " est : " + this.getFrom()+ "\n";
        res += "La valeur du champ " + "ContentNotif" + " est : " + this.getContentNotif()+ "\n";
        res += "La valeur du champ " + "Accept" + " est : " + this.isAccept()+ "\n";
        res += "La valeur du champ " + "CatNotification" + " est : " + this.getCatNotification()+ "\n";
        res += "La valeur du champ " + "SpecialId" + " est : " + this.getSpecialId() + "\n";
        return res;
    }

    public static  void main(String args[]){
        User u1 = new User(4,"Maxime","maxime@gmail.com","mdp");
        User u2 = new User(1,"Dorian","dorian@gmail.com","mdp");
        NotificationCategory n1 = NotificationCategory.createNotification("friendInvitation");
        NotificationCategory n2 = NotificationCategory.createNotification("serverNotification");

        Notification t1 = new Notification(1,u1,u2,"Demande de d'ami Dorian => Maxime",true,n1,u2.getId());
        Notification t5 = new Notification(u1,u2,"Demande de d'ami Dorian => Maxime",true,n1,u2.getId());
        Notification t2 = new Notification(u1,"Message Server",true,n1,u2.getId());
        Notification t3 = new Notification(u1,"Message Server",true,n1);
        Notification t4 = new Notification(u1,u1,"Demande d'ami de Maxime => Maxime",true,n1);


        System.out.println("On test NotifClassique : \n" + t1);
        System.out.println("On test NotifClassiqueSansIdOPréliminaire = Insert : \n" + t5);
        System.out.println("On test NotifSansSender : \n" + t2);
        System.out.println("On test NotifSansId&Sender : \n" + t3);
        System.out.println("On test NotifSansId : \n" + t4);

    }
}
