package togaether.DB;

import togaether.BL.Model.Collaborator;

import java.sql.SQLException;

public interface CollaboratorDAO {

  public Collaborator getCollaboratorByUserIdAndTravelId(int user_id, int travel_id) throws SQLException;
}
