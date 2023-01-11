package togaether.BL.Facade;

import togaether.BL.Model.Budget;
import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.BL.TogaetherException.CollaboratorNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorFacade {

    private static CollaboratorFacade instance = new CollaboratorFacade();

    public static CollaboratorFacade getInstance(){return instance;}

    /**
     * Query the creation of a new Collaborator in the Database
     * @param collaborator
     * @return the id of the newly created Collaborator
     */
    public int createCollaborator(Collaborator collaborator){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();
        int id = 0;
        try{
            id = cdaop.createCollaborator(collaborator);
            TrophyFacade.getInstance().isTrophyValidForUser(UserFacade.getInstance().getConnectedUser().getId(),"travel");
        }catch(SQLException e){
            System.out.println(e);
        }
        return id;
    }

    /**
     * Query the update of a specific Collaborator
     * @param collaborator
     */
    public void updateCollaborator(Collaborator collaborator){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.updateCollaborator(collaborator);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Query the list of all Collaborator of a specific Travel
     * @param travel
     * @return a list of collaborator
     */
    public List<Collaborator> findCollaboratorByTravel(Travel travel){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        List<Collaborator> collaborators = new ArrayList<>();
        try{
            collaborators = cdaop.findCollaboratorByTravel(travel);
        }catch(SQLException e){
            System.out.println(e);
        }
        return collaborators;
    }

    /**
     * Fetch a list of collaborator that a user is.
     * @param user
     * @return a list of Collaborator
     */
    public List<Collaborator> findCollaboratorByUser(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        List<Collaborator> collaborators = new ArrayList<>();
        try{
            collaborators = cdaop.findCollaboratorByUser(user);
        }catch(SQLException e){
            System.out.println(e);
        }

        return collaborators;
    }

    /**
     * Quuery the collaborator associate to a user and a specific travel
     * @param user
     * @param travel
     * @return
     * @throws CollaboratorNotFoundException
     */
    public Collaborator findCollaboratorByUserAndTravel(User user, Travel travel) throws CollaboratorNotFoundException {
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            Collaborator collab = cdaop.getCollaboratorByUserIdAndTravelId(user.getId(), travel.getIdTravel());
            if(collab == null){
                throw new CollaboratorNotFoundException();
            }else{
                return collab;
            }
        }catch(SQLException e){
            throw new CollaboratorNotFoundException();
        }
    }

    /**
     * Fetch all collaborators that still are not associate to a user in a specific travel given in parameter
     * @param travel
     * @return a list of Collaborators
     * @throws CollaboratorNotFoundException
     */
    public List<Collaborator> findCollaboratorNotChosenByTravel(Travel travel) throws CollaboratorNotFoundException {
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            return cdaop.findCollaboratorNotChosenByTravel(travel);
        }catch(SQLException e){
            throw new CollaboratorNotFoundException();
        }
    }

    /**
     * Fetch a specific collaborator depending on its id
     * @param id
     * @return a specific collaborator
     * @throws CollaboratorNotFoundException
     */
    public Collaborator findCollaboratorById(int id) throws CollaboratorNotFoundException{
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            return cdaop.findCollaboratorById(id);
        }catch(SQLException e){
            throw new CollaboratorNotFoundException();
        }
    }

    /**
     * Delete all collaborator associated to a user
     * @param user
     */
    public void deleteAllColaboratorByUser(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.deleteAllColaboratorByUser(user);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Delete all collaborator associated to a specific travel
     * @param travel
     */
    public void deleteAllColaboratorByTravel(Travel travel){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.deleteAllCollaboratorByTravel(travel);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * Delete a specific collaborator
     * @param collaborator
     */
    public void deleteColaborator(Collaborator collaborator){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.deleteCollaborator(collaborator);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

}
