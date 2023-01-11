package togaether.BL.Facade;

import togaether.BL.Model.*;
import togaether.BL.TogaetherException.CollaboratorNotFoundException;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.BL.TogaetherException.TravelNotFoundException;
import togaether.BL.TogaetherException.UserNotFoundException;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.*;

public class ChatFacade {
  static private final ChatFacade instance = new ChatFacade();

  public static ChatFacade getInstance() {
    return instance;
  }

  /**
   * Send a message in the actual Travel's chat
   * @param text
   * @return
   */
  public boolean sendMessage(String text) {
    TravelFacade travelFacade = TravelFacade.getInstance();

    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();

    try {
      messageDB.insertMessage(travelFacade.getTravel().getIdTravel(), travelFacade.getCollaborator().getId(), text);
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  /**
   * Delete a Message
   * @param message
   * @throws SQLException
   */
  public void deleteMessage(Message message) throws SQLException{
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    messageDB.deleteMessageById(message.getId());
  }

  /**
   * Delete Messages in Travel
   * @param travel
   * @throws SQLException
   */
  public void deleteMessagesInTravel(Travel travel) throws SQLException{
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    messageDB.deleteMessageByTravelId(travel.getIdTravel());
  }

  /**
   * Return a List of Messages of a Collaborator
   * @param id
   * @return
   * @throws CollaboratorNotFoundException
   * @throws DBNotFoundException
   */
  public List<Message> getMessagesByCollaboratorId(int id) throws CollaboratorNotFoundException, DBNotFoundException {
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();

    try {
      return messageDB.findMessagesByCollaboratorId(id);
    } catch (SQLException e) {
      throw new DBNotFoundException();
    }
  }

  /**
   * Return a List of Messages of an User
   * @param id
   * @return
   * @throws UserNotFoundException
   * @throws DBNotFoundException
   */
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

  /**
   * Return a List of Messages of a Travel
   * @param id
   * @return
   * @throws TravelNotFoundException
   * @throws DBNotFoundException
   */
  public List<Message> getMessagesByTravelId(int id) throws TravelNotFoundException, DBNotFoundException{
    List<Message> liste;
    try {
      AbstractFactory abstractFactory = AbstractFactory.createInstance();
      TravelDAO travelDB = abstractFactory.getTravelDAO();
      MessageDAO messageDB = abstractFactory.getMessageDAO();
      Travel t = travelDB.findTravelById(id);
      if(t != null) {
        liste = messageDB.findMessagesByTravelId(id);
      }
      else
        throw new TravelNotFoundException();
    } catch (SQLException e) {
      System.out.println(e);
      throw new DBNotFoundException();
    }
    return liste;
  }

  /**
   * Return a List of Messages of a User in a Travel
   * @param idUser
   * @param idTravel
   * @return
   * @throws UserNotFoundException
   * @throws TravelNotFoundException
   * @throws DBNotFoundException
   */
  public List<Message> getMessagesByUserIdAndTravelId(int idUser, int idTravel) throws UserNotFoundException,TravelNotFoundException, DBNotFoundException{
    AbstractFactory fact = AbstractFactory.createInstance();
    MessageDAO messageDB = fact.getMessageDAO();
    TravelDAO travelDB = fact.getTravelDAO();
    UserDAO userDB = fact.getUserDAO();

    try {
      Travel t = travelDB.findTravelById(idTravel);
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
