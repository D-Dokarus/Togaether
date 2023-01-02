package togaether.BL.Facade;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Notification;
import togaether.BL.Model.Travel;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TravelFacade {

    private Travel travel = null;

    private Collaborator collaborator = null;

    private List<Collaborator> collaborators = null;

    static private TravelFacade instance = new TravelFacade();

    public static TravelFacade getInstance() {
        return instance;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public Collaborator getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(Collaborator collaborator) {
        this.collaborator = collaborator;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Query the archiving of the actual travel in DB
     */
    public void archiveTravel(Travel travel) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();
        try {
            travelDB.archiveTravel(travel);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the unarchiving of the actual travel in DB
     */
    public void unarchiveTravel(Travel travel) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();
        try {
            travelDB.unarchiveTravel(travel);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the creation of a specific travel (given in parameters) in DB
     * @param travel
     */
    public void createTravel(Travel travel) throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();

        try {
            travelDB.createTravel(travel);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the update of a specific travel (given in parameters) in DB
     * @param travel
     */
    public void updateTravel(Travel travel) throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();

        try {
            travelDB.updateTravel(travel);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the search for a specific path by an Id (given in parameters) in the DB
     * @param Id
     */
    public Travel findTravelById(Integer Id) throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();

        try {
            return travelDB.findTravelById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }


    /**
     * Query the supression of a specific travel (given in parameters) in DB
     * @param travel
     */
    public void deleteTravel(Travel travel) throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();

        try {
            travelDB.deleteTravelById(travel.getIdTravel());
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    /**
     * Query the archiving of the actual travel in DB
     */
    public List<Travel> travelsArchived(int Id) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();
        try {
             return travelDB.travelsArchived(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }

    public Travel findLatestCreatedTravel() throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDB = fact.getTravelDAO();
        try {
            return travelDB.findLatestCreatedTravel();
        } catch (SQLException e) {
            System.out.println(e);
            throw new DBNotFoundException();
        }
    }


    public List<Travel> findTravelsByUserId(int id) {
        ArrayList<Travel> liste = new ArrayList<>();
        TravelDAO travelDB = AbstractFactory.createInstance().getTravelDAO();
        try {
            liste = (ArrayList<Travel>) travelDB.findTravelsByUserId(id);
        } catch (SQLException e) {}
        return liste;
    }
}
