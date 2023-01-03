package togaether.BL.Facade;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorFacade {

    private static CollaboratorFacade instance = new CollaboratorFacade();

    public static CollaboratorFacade getInstance(){return instance;}

    public int createCollaborator(Collaborator collaborator){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            return cdaop.createCollaborator(collaborator);
        }catch(SQLException e){
            System.out.println(e);
        }
        return 0;
    }

    public void updateCollaborator(Collaborator collaborator){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.updateCollaborator(collaborator);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

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

    public Collaborator findCollaboratorByUserAndTravel(User user, Travel travel) throws CollaboratorNotFoundException {
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            return cdaop.getCollaboratorByUserIdAndTravelId(user.getId(), travel.getIdTravel());
        }catch(SQLException e){
            throw new CollaboratorNotFoundException();
        }
    }

    public Collaborator findCollaboratorById(int id) throws CollaboratorNotFoundException{
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            return cdaop.findCollaboratorById(id);
        }catch(SQLException e){
            throw new CollaboratorNotFoundException();
        }
    }

    public void deleteAllColaboratorByUser(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.deleteAllColaboratorByUser(user);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    public void deleteAllColaboratorByTravel(Travel travel){
        AbstractFactory af = AbstractFactory.createInstance();
        CollaboratorDAO cdaop = af.getCollaboratorDAO();

        try{
            cdaop.deleteAllCollaboratorByTravel(travel);
        }catch(SQLException e){
            System.out.println(e);
        }
    }

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
