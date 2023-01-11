package togaether.BL.Facade;

import togaether.BL.Model.*;
import togaether.DB.AbstractFactory;
import togaether.DB.TrophyDAO;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrophyFacade {

    private Trophy trophy = null;

    static private final TrophyFacade instance = new TrophyFacade();

    public static TrophyFacade getInstance() {
        return instance;
    }

    /**
     * Create a Trophy
     * @param trophy
     * @throws SQLException
     */
    public void createTrophy(Trophy trophy) throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        trophyDB.createTrophy(trophy.getName(), trophy.getCategory_id(), trophy.getValue(), trophy.getImage());
    }

    /**
     * Update a Trophy
     * @param trophy
     * @throws SQLException
     */
    public void updateTrophy(Trophy trophy) throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        trophyDB.updateTrophy(trophy);
    }

    /**
     * Delete a Trophy
     * @param trophy
     * @throws SQLException
     */
    public void deleteTrophy(Trophy trophy) throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        trophyDB.deleteTrophyById(trophy.getId());
    }

    /**
     * Return a List of all the trophies
     * @return
     * @throws SQLException
     */
    public List<Trophy> getAllTrophies() throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        return trophyDB.findAllTrophies();
    }

    /**
     * Return a List of all TrophyCategories
     * @return
     * @throws SQLException
     */
    public List<TrophyCategory> getAllCategories() throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        return trophyDB.findAllCategories();
    }

    /**
     * Return a List of the trophies owned by the connected User
     * @return
     * @throws SQLException
     */
    public List<Trophy> getTrophiesOfUser() throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        return trophyDB.findTrophiesByUserId(UserFacade.getInstance().getConnectedUser().getId());
    }

    /**
     * Verify if the trophies of a category can be give to the connected User, and give them if yes
     * @param category
     * @return
     */
    public void isTrophyValidForUser(int user_id, String category) {
        try {
            TrophyDAO trophyDB = AbstractFactory.createInstance().getTrophyDAO();
            ArrayList<Trophy> trophies = (ArrayList<Trophy>) trophyDB.findNotOwnedTrophiesByCategories(category, user_id);
            ArrayList<Trophy> trophiesSuccessed = new ArrayList<Trophy>();
            int count = 0;
            if (category.equals("travel")) {
                count = trophyDB.countAllParticipatingTravelsByUserId(user_id);
            } else if (category.equals("friend")) {
                count = trophyDB.countFriendsByUserId(user_id);
            } else System.out.println("Cette catégorie n'a pas de test de succès");

            // Vérifier les trophées obtenus
            for (Trophy trophy : trophies) {
                if (trophy.getValue() <= count) {
                    trophiesSuccessed.add(trophy);
                }
            }
            //Donner les trophées obtenus réussis
            for (Trophy trophy : trophiesSuccessed) {
                this.giveTrophyToUser(user_id, trophy.getId());
                User faux = new User(user_id);
                Notification notification = new Notification(faux, "Nouveau trophée obtenu ! : "+trophy.getName(), false, NotificationCategory.createNotification("trophyObtained"));
                NotificationFacade.getInstance().createNotification(notification);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du test des trophées :" + e);
        }
    }

    /**
     * Return a String that specify the condition to gain the specific Trophy
     * @param trophy
     * @return
     */
    public String getCondition(Trophy trophy) {
        String category = trophy.getCategory();
        if (category.equals("travel"))
            return "Être dans au moins " + trophy.getValue() + " voyage(s)";
        else if (category.equals("message"))
            return "Avoir envoyé un total de " + trophy.getValue() + " messages";
        else if (category.equals("friend"))
            return "Être ami avec " + trophy.getValue() + " utilisateur(s)";
        else if (category.equals("depense"))
            return "Avoir dépensé " + trophy.getValue() + " euros en un seul voyage";
        else if (category.equals(""))
            return "";
        else
            return "";
    }

    /**
     * Return the path of the Trophy's image
     * @param trophy
     * @param isOwned
     * @return
     * @throws MalformedURLException
     */
    public String getImagePath(Trophy trophy, boolean isOwned) throws MalformedURLException {
        String sep = File.separator;
        String path = "";
        if (isOwned)
            path = System.getProperty("user.dir") + sep + "image" + sep + "trophy" + sep + "owned_" + trophy.getImage();
        else
            path = System.getProperty("user.dir") + sep + "image" + sep + "trophy" + sep + trophy.getImage();

        File image = new File(path);
        if (image.exists()) {

            path = image.toURI().toURL().toExternalForm();

        } else {
            path = new File(System.getProperty("user.dir") + sep + "image" + sep + "trophy" + sep + "unknow.jpg").toURI().toURL().toExternalForm();
        }
        return path;
    }

    /**
     * Give a Trophy to the connected User
     * @param trophy_id
     * @throws SQLException
     */
    public void giveTrophyToUser(int user_id, int trophy_id) throws SQLException {
        AbstractFactory abstractFactory = AbstractFactory.createInstance();
        TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
        trophyDB.createTrophyUser(trophy_id, user_id);
    }

    /**
     * Return a String that represents the description of a TrophyCategory name
     * @param name
     * @return
     */
    public String nameToFrench(String name) {
        if (name.equals("travel"))
            return "Voyages total";
        else if (name.equals("message"))
            return "Messages total";
        else if (name.equals("friend"))
            return "Amis total";
        else if (name.equals("expense"))
            return "Dépenses en un voyage";
        return "nameToFrench de TrophyFacade";
    }

    /**
     * Return a String that give the name of a TrophyCategory from a description
     * @param french
     * @return
     */
    public String frenchToName(String french) {
        if (french.equals("Voyages total"))
            return "travel";
        else if (french.equals("Messages total"))
            return "message";
        else if (french.equals("Amis total"))
            return "friend";
        else if (french.equals("Dépenses en un voyage"))
            return "expense";
        return "frenchToName de TrophyFacade";
    }

    /**
     * Return the actual Trophy
     * @return
     */
    public Trophy getTrophy() {
        return trophy;
    }

    /**
     * Set the actual Trophy
     * @param trophy
     */
    public void setTrophy(Trophy trophy) {
        this.trophy = trophy;
    }
}
