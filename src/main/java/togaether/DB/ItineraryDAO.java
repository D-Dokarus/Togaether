package togaether.DB;

import java.sql.SQLException;
import java.util.List;

public interface ItineraryDAO {

    public void createItinerary(Itinerary itinerary) throws SQLException;

    public void deleteItineraryById(int Id) throws SQLException;

    public Itinerary findItineraryById(int Id) throws SQLException;

    public List<Itinerary> findItinerariesByTravelId(int Id) throws SQLException;

    public void updateItinerary(Itinerary itinerary) throws SQLException;

    public void deleteItinerariesByTravelId(int Id) throws SQLException;
}
