package togaether.DB;

import togaether.BL.Model.Itinerary;
import togaether.BL.Model.TransportCategory;

import java.sql.SQLException;
import java.util.List;

public interface ItineraryDAO {

    public int createItinerary(Itinerary itinerary) throws SQLException;

    public void deleteItineraryById(int Id) throws SQLException;

    public Itinerary findItineraryById(int Id) throws SQLException;

    public List<Itinerary> findItinerariesByTravelId(int Id) throws SQLException;

    public void updateItinerary(Itinerary itinerary) throws SQLException;

    public void updateIndexBeforeItineraryById(Integer id, Integer indexBefore) throws SQLException;

    public void updateIndexAfterItineraryById(Integer id, Integer indexAfter) throws SQLException;

    public void updateIndexItinerary(Itinerary itinerary) throws SQLException;

    public void deleteItinerariesByTravelId(int Id) throws SQLException;

    public String findNameCatTransportById(int Id) throws SQLException;

    public int findIdCatTransportByName(String name) throws SQLException;

    public List<TransportCategory> findAllCatTransport() throws SQLException;

    public void switchIndexFor2ItinerariesById(int A,int B) throws SQLException;

    public void switchIndexBeforeFor3ItinerariesById(int A,int B,int C) throws SQLException;

    public void switchIndexAfterFor3ItinerariesById(int A,int B,int C) throws SQLException;

    public void switchIndexFor4ItinerariesById(int A,int B,int C, int D) throws SQLException;
}
