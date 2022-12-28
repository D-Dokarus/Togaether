package togaether.DB;

import togaether.BL.Model.Travel;

import java.sql.SQLException;
import java.util.List;

public interface TravelDAO {

    void createTravel(Travel travel) throws SQLException;

    void deleteTravelById(int Id) throws SQLException;

    Travel findTravelById(int Id) throws SQLException;

    List<Travel> findTravelsByUserId(int Id) throws SQLException;

    List<Travel> findTravelsByUserIdAndString(int Id, String str) throws SQLException;

    void updateTravel(Travel travel) throws SQLException;

    void updateOwnerTravel(Travel travel) throws SQLException;

    void archiveTravel(Travel travel) throws SQLException;

    void unarchiveTravel(Travel travel) throws SQLException;

}
