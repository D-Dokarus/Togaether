package togaether.DB;

import togaether.BL.Model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDAO {
  public void insertMessage(int travel_d, int collaborator_id, String message_content) throws SQLException;
  public List<Message> findMessagesByCollaboratorId(int id) throws SQLException;
  public List<Message> findMessagesByUserId(int id) throws SQLException;
  public List<Message> findMessagesByTravelId(int id) throws SQLException;
  public List<Message> findMessagesByUserIdAndTravelId(int idUser, int idTravel) throws SQLException;
}

