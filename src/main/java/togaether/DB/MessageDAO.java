package togaether.DB;

import togaether.BL.Model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
  /**
   * Create a new Message in a travel
   * @param travel_d
   * @param collaborator_id
   * @param message_content
   * @throws SQLException
   */
  public void insertMessage(int travel_d, int collaborator_id, String message_content) throws SQLException;

  /**
   * Delete a Message
   * @param message_id
   * @throws SQLException
   */
  public void deleteMessageById(int message_id) throws SQLException;

  /**
   * Delete Messages from a Travel
   * @param travel_id
   * @throws SQLException
   */
  public void deleteMessageByTravelId(int travel_id) throws SQLException;
  /**
   * Return a List of the messages of a Collaborator
   * @param id
   * @return
   * @throws SQLException
   */
  public List<Message> findMessagesByCollaboratorId(int id) throws SQLException;

  /**
   * Return a List of the messages of an User
   * @param id
   * @return
   * @throws SQLException
   */
  public List<Message> findMessagesByUserId(int id) throws SQLException;

  /**
   * Return a List of the messages of a Travel
   * @param id
   * @return
   * @throws SQLException
   */
  public List<Message> findMessagesByTravelId(int id) throws SQLException;

  /**
   * Return a List of the messages send by an User in a Travel
   * @param idUser
   * @param idTravel
   * @return
   * @throws SQLException
   */
  public List<Message> findMessagesByUserIdAndTravelId(int idUser, int idTravel) throws SQLException;
}

