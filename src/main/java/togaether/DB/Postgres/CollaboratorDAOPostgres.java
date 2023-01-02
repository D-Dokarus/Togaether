package togaether.DB.Postgres;

import togaether.BL.Model.Collaborator;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Message;
import togaether.BL.Model.User;
import togaether.DB.CollaboratorDAO;
import togaether.DB.MessageDAO;

import java.sql.*;
import java.util.*;

public class CollaboratorDAOPostgres implements CollaboratorDAO {
  PostgresFactory postgres;

  public CollaboratorDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }
  @Override
  public Collaborator getCollaboratorByUserIdAndTravelId(int user_id, int travel_id) throws SQLException {
    Collaborator collaborator = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM collaborator WHERE c.user_id =? AND c.travel_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1,user_id);
        statement.setInt(2,travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
             collaborator = new Collaborator(resultSet.getInt("collaborator_id"), resultSet.getInt("travel_id"), resultSet.getInt("user_id"), resultSet.getString("collaborator_name"));
          }
        }
      }
    }
    return collaborator;
  }
}
