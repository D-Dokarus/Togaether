package togaether.BL.Model;

import java.time.LocalDateTime;

/**
 * Model d'un user
 */
public class User {

    int id;
    private String name;
    private String surname;
    private String pseudo;
    private String email;
    private String password;
    private String country;
    private String dateCreation;
    private Boolean isAdmin = true;


    public User(int id, String name, String surname, String pseudo, String email, String password, String country, String dateCreation) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.country = country;
        this.dateCreation = dateCreation;
        this.isAdmin = true;
    }

    public User(String name, String surname, String pseudo, String email, String password, String country) {
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
        this.country = country;
        this.dateCreation = createDate();
        this.isAdmin = true;
    }

    public User(String name, String surname, String pseudo, String email, String country) {
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.email = email;
        this.country = country;
        this.dateCreation = createDate();
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String surname, String pseudo, String email, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pseudo = pseudo;
        this.email = email;
        this.country = country;
        this.dateCreation = createDate();
    }

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String createDate() {
        LocalDateTime t = LocalDateTime.now();
        String date = t.getDayOfMonth() + "/" + t.getMonthValue() + "/" + t.getYear();
        return date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getCountry() {
        return country;
    }

    public String getDateCreation() {
        return dateCreation;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }

}
