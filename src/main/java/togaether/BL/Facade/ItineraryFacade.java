package togaether.BL.Facade;

import org.springframework.security.crypto.bcrypt.BCrypt;
import togaether.BL.Model.Itinerary;
import togaether.BL.Model.TransportCategory;
import togaether.BL.Model.User;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.BL.TogaetherException.UserBadPasswordException;
import togaether.BL.TogaetherException.UserNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.ItineraryDAO;
import togaether.DB.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class ItineraryFacade {

    static private ItineraryFacade instance = new ItineraryFacade();

    public static ItineraryFacade getInstance() {
        return instance;
    }

    public int createItinerary(Itinerary itinerary) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.createItinerary(itinerary);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public void deleteItineraryById(int Id) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            itineraryDAO.deleteItineraryById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public Itinerary findItineraryById(int Id) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.findItineraryById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public List<Itinerary> findItinerariesByTravelId(int Id) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.findItinerariesByTravelId(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public void updateItinerary(Itinerary itinerary) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            itineraryDAO.updateItinerary(itinerary);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public void deleteItinerariesByTravelId(int Id) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            itineraryDAO.deleteItinerariesByTravelId(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public String findNameCatTransportById(int Id) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.findNameCatTransportById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public int findIdCatTransportByName(String name) throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.findIdCatTransportByName(name);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public List<TransportCategory> findAllCatTransport() throws SQLException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItinerary();

        try {
            return itineraryDAO.findAllCatTransport();
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

}
