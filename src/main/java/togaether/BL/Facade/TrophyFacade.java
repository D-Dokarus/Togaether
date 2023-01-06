package togaether.BL.Facade;

import javafx.scene.image.ImageView;
import togaether.BL.Model.*;
import togaether.DB.AbstractFactory;
import togaether.DB.TrophyDAO;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrophyFacade {

  private Trophy trophy = null;

  static private final TrophyFacade instance = new TrophyFacade();

  public static TrophyFacade getInstance() {
    return instance;
  }

  public void createTrophy(Trophy trophy) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    trophyDB.createTrophy(trophy.getName(), trophy.getCategory_id(), trophy.getValue(), trophy.getImage());
  }
  public void updateTrophy(Trophy trophy) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    trophyDB.updateTrophy(trophy);
  }
  public void deleteTrophy(Trophy trophy) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    trophyDB.deleteTrophyById(trophy.getId());
  }
  public List<Trophy> getAllTrophies() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    return trophyDB.findAllTrophies();
  }
  public List<TrophyCategory> getAllCategories() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    return trophyDB.findAllCategories();
  }
  public List<Trophy> getTrophiesOfUser() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    return trophyDB.findTrophiesByUserId(UserFacade.getInstance().getConnectedUser().getId());
  }
  public boolean isTrophyValidForUser(String category) {
    Boolean valid = false;
    try {
      TrophyDAO trophyDB = AbstractFactory.createInstance().getTrophyDAO();
      ArrayList<Trophy> trophies = (ArrayList<Trophy>) trophyDB.findNotOwnedTrophiesByCategories(category, UserFacade.getInstance().getConnectedUser().getId());
      ArrayList<Trophy> trophiesSuccessed = new ArrayList<Trophy>();
      int count = 0;
      if(category.equals("travel")) {
        count = trophyDB.countAllParticipatingTravelsByUserId(UserFacade.getInstance().getConnectedUser().getId());
      }
      else if(category.equals("")) {

      }
      else System.out.println("Cette catégorie n'a pas de test de succès");

      // Vérifier les trophées obtenus
      for (Trophy trophy: trophies) {
        if (trophy.getValue() <= count) {
          trophiesSuccessed.add(trophy);
        }
      }
      //Donner les trophées obtenus réussis
      for (Trophy trophy: trophiesSuccessed) {
        this.giveTrophyToUser(trophy.getId());
        Notification notification = new Notification(UserFacade.getInstance().getConnectedUser(), trophy.getName(), false, NotificationCategory.createNotification("trophyObtained"));
        NotificationFacade.getInstance().createNotification(notification);
      }
    } catch (SQLException e) {
      System.out.println("Erreur lors du test des trophées :"+e);
    }
    return valid;
  }
  public String getCondition(Trophy trophy) {
    String category = trophy.getCategory();
    if(category.equals("travel"))
      return "Être dans au moins "+trophy.getValue()+" voyage(s)";
    else if(category.equals("message"))
      return "Avoir envoyé un total de "+trophy.getValue()+" messages";
    else if(category.equals("friend"))
      return "Être ami avec "+trophy.getValue()+" utilisateur(s)";
    else if(category.equals("depense"))
      return "Avoir dépensé "+trophy.getValue()+" euros en un seul voyage";
    else if(category.equals(""))
      return "";
    else
      return "";
  }
  public String getImagePath(Trophy trophy, boolean isOwned) {
    File image;
    if(isOwned)
      image = new File(System.getProperty("user.dir")+"\\image\\trophy\\owned_"+trophy.getImage());
    else
      image = new File(System.getProperty("user.dir")+"\\image\\trophy\\"+trophy.getImage());
    ImageView imageView;
    if(image.exists())
      return image.getAbsolutePath();
    else
      return System.getProperty("user.dir")+".\\image\\trophy\\unknow.jpg";
  }
  public void giveTrophyToUser(int trophy_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    trophyDB.createTrophyUser(trophy_id, UserFacade.getInstance().getConnectedUser().getId());
  }

  public String nameToFrench(String name) {
    if(name.equals("travel"))
      return "Voyages total";
    else if(name.equals("message"))
      return "Messages total";
    else if(name.equals("friend"))
      return "Amis total";
    else if(name.equals("expense"))
      return "Dépenses en un voyage";
    return "nameToFrench de TrophyFacade";
  }
  public String frenchToName(String french) {
    if(french.equals("Voyages total"))
      return "travel";
    else if(french.equals("Messages total"))
      return "message";
    else if(french.equals("Amis total"))
      return "friend";
    else if(french.equals("Dépenses en un voyage"))
      return "expense";
    return "frenchToName de TrophyFacade";
  }

  public Trophy getTrophy() {
    return trophy;
  }

  public void setTrophy(Trophy trophy) {
    this.trophy = trophy;
  }
}
