package togaether.BL.Facade;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Itinerary;
import togaether.BL.Model.Travel;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TravelFacade {

    private Travel travel = null;

    private Collaborator collaborator = null;

    private List<Collaborator> collaborators = null;

    private LinkedList<Itinerary> itineraries = new LinkedList<Itinerary>();

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

    public LinkedList<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(LinkedList<Itinerary> itineraries) {
        this.itineraries = itineraries;
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
    public int createTravel(Travel travel) throws DBNotFoundException{
        AbstractFactory fact = AbstractFactory.createInstance();
        TravelDAO travelDAO = fact.getTravelDAO();

        try {
            return travelDAO.createTravel(travel);
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
             return travelDB.findTravelsArchivedByUserId(Id);
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
            return liste = (ArrayList<Travel>) travelDB.findTravelsByUserId(id);
        } catch (SQLException e) {}
        return liste;
    }
    public List<Travel> findTravelsParticipationByUserId(int id) {
        ArrayList<Travel> liste = new ArrayList<>();
        TravelDAO travelDB = AbstractFactory.createInstance().getTravelDAO();
        try {
            liste = (ArrayList<Travel>) travelDB.findTravelsParticipationByUserId(id);
        } catch (SQLException e) {}
        return liste;
    }
    public List<Travel> findTravelsArchivedParticipationByUserId(int id) {
        ArrayList<Travel> liste = new ArrayList<>();
        TravelDAO travelDB = AbstractFactory.createInstance().getTravelDAO();
        try {
            liste = (ArrayList<Travel>) travelDB.findTravelsArchivedParticipationByUserId(id);
        } catch (SQLException e) {}
        return liste;
    }

    public LinkedList<Itinerary> findItineraries(int id){
        ArrayList<Itinerary> list = new ArrayList<Itinerary>();
        AbstractFactory fact = AbstractFactory.createInstance();
        ItineraryDAO itineraryDAO = fact.getItineraryDAO();
        this.itineraries.clear();
        try {
            list.addAll(itineraryDAO.findItinerariesByTravelId(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(list.size()!=0){
            int i = 0;
            boolean found = false;
            int searchItinerary = 0;
            while(!found){
                if(list.get(i).getIndexBefore()==-1){ // First indexBefore element of the linkedlist in DB have the value -1
                    this.itineraries.addFirst(list.get(i));
                    searchItinerary = list.get(i).getIndexAfter();
                    list.remove(i);
                    found = true;
                }
                i++;
            }
            if(searchItinerary==-1){// Last indexAfter element of the linkedlist in DB have the value -1
                return this.itineraries;
            }else {
                found = false;
                i = 0;
                while(list.size() > 0){
                    while(!found){
                        if(list.get(i).getItinerary_id()==searchItinerary){
                            this.itineraries.addLast(list.get(i));
                            if((list.size()-1)>0){
                                searchItinerary = list.get(i).getIndexAfter();
                            }
                            list.remove(i);
                            found = true;
                        }
                        i++;
                    }
                    found = false;
                    i = 0;
                }
            }
        }

        return this.itineraries;
    }
}
