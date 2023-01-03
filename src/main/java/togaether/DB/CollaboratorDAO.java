package togaether.DB;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface CollaboratorDAO {

  public Collaborator getCollaboratorByUserIdAndTravelId(int user_id, int travel_id) throws SQLException;
  public int createCollaborator(Collaborator collaborator) throws SQLException;
  public void deleteCollaborator(Collaborator collaborator) throws SQLException;
  public void deleteAllCollaboratorByTravel(Travel travel) throws SQLException;
  public void updateCollaborator(Collaborator collaborator) throws SQLException;

  public List<Collaborator> findCollaboratorByTravel(Travel travel) throws SQLException;
  public List<Collaborator> findCollaboratorByUser(User user) throws SQLException;
  public Collaborator findCollaboratorById(int id) throws SQLException;
  public void deleteAllColaboratorByUser(User user) throws SQLException;
  public void setAllUser_IdToNullByUser(User user) throws SQLException;

}
