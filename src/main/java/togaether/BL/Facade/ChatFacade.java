package togaether.BL.Facade;

import togaether.BL.Model.Message;
import togaether.BL.Model.User;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.*;

public class ChatFacade {
  static private ChatFacade instance = new ChatFacade();

  public static ChatFacade createInstance() {
    return instance;
  }

  public boolean sendMessage(String text) {
    //TO DO
    //TravelFacade travelFacade = TravelFacade.createInstance();
    UserFacade userFacade = UserFacade.createInstance();

    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();

    try {
      //TO DO
      //messageDB.insertMessage(1, userFacade.getConnectedUser().getId(), sqlDate, text);
      messageDB.insertMessage(1, 1, text);
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  public List<Message> getMessagesByUserId(int id) throws UserNotFoundException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    UserDAO userDB = fact.getUserDAO();

    try {
      User u = userDB.findByUserId(id);
      if(!(u == null)) {
        List<Message> liste = messageDB.findMessagesByUserId(id);
        return liste;
      }
      else
        throw new UserNotFoundException();
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }
  public List<Message> getMessagesByTravelId(int id) throws TravelNotFoundException, DBNotFoundException{
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    //TO DO
    //TravelDAO travelDB = fact.getTravelDAO();

    try {
      //TO DO
      //Travel t = travelDB.findByTravelId(id);
      Integer t = 1;
      if(t != null) {
        return messageDB.findMessagesByTravelId(id);
      }
      else
        throw new TravelNotFoundException();
    } catch (SQLException e) {
      System.out.println(e);
      throw new DBNotFoundException();
    }
  }
  public List<Message> getMessagesByUserIdAndTravelId(int idUser, int idTravel) throws UserNotFoundException,TravelNotFoundException, DBNotFoundException{
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    //TO DO
    //TravelDAO travelDB = fact.getTravelDAO();
    UserDAO userDB = fact.getUserDAO();

    try {
      //TO DO
      //Travel t = travelDB.findByTravelId(idTravel);
      Integer t = 1;
      User u = userDB.findByUserId(idUser);
      if(t != null && u != null) {
        List<Message> liste = messageDB.findMessagesByUserIdAndTravelId(idUser, idTravel);
        return liste;
      }
      else if(t == null)
        throw new TravelNotFoundException();
      else
        throw new UserNotFoundException();
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }
}
