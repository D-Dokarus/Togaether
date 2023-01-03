package togaether.BL.Facade;

import togaether.BL.Model.Trophy;
import togaether.DB.AbstractFactory;
import togaether.DB.TrophyDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrophyFacade {
  static private final TrophyFacade instance = new TrophyFacade();

  public static TrophyFacade getInstance() {
    return instance;
  }

  public List<Trophy> getAllTrophies() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    return trophyDB.findAllTrophies();
  }
  public List<Trophy> getTrophiesOfUser() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    return trophyDB.findTrophiesByUserId(UserFacade.getInstance().getConnectedUser().getId());
  }
  public boolean isTrophyValidForUser(Trophy trophy) {
    Boolean valid = false;
    return valid;
  }
  public String getCondition(String category) {
    if(category.equals("travel"))
      return "Être dans un certain nombre de voyages";
    else
      return "";
  }
  public void giveTrophyToUser(int trophy_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    TrophyDAO trophyDB = abstractFactory.getTrophyDAO();
    trophyDB.createTrophyUser(trophy_id, UserFacade.getInstance().getConnectedUser().getId());
  }
}
