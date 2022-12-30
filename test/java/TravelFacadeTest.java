import org.springframework.cglib.core.Local;
import togaether.BL.Facade.DBNotFoundException;
import togaether.BL.Facade.TravelFacade;
import org.junit.jupiter.api.*;
import togaether.BL.Model.Travel;
import togaether.BL.Model.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;


public class TravelFacadeTest{
    User owner;
    LocalDate actual;
    LocalDate end;
    Travel travelToCreate;
    Travel createdTravel;
    int idOfNewTravelCreated;

    TravelFacadeTest(){
        this.owner = new User(4,"Maxime","maxime@gmail.com","1234");
        this.actual = LocalDate.now();
        this.end = LocalDate.now();
        this.travelToCreate = new Travel(owner,"TRAVEL_TEST","TRAVEL_TEST_DESCRIPTION",java.sql.Date.valueOf(actual),java.sql.Date.valueOf(end),false);
    }


    int createTravel(){

        int res = 0;
        try{
            TravelFacade.createInstance().createTravel(this.travelToCreate);
            Travel latestCreated = TravelFacade.createInstance().findLatestCreatedTravel();

            if(latestCreated.getNameTravel().equals(this.travelToCreate.getNameTravel())){
                this.idOfNewTravelCreated = latestCreated.getIdTravel();
                this.createdTravel = latestCreated;
                res = 1;
            }

        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }


    int findTravelById(){
        int res = 0;
        try{
            Travel latest = TravelFacade.createInstance().findTravelById(this.createdTravel.getIdTravel());
            if(this.createdTravel.getIdTravel() == latest.getIdTravel()){
                res = 1;
            }


        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }


    int findLatestCreatedTravel(){
        int res = 0;
        try{
            Travel latest = TravelFacade.createInstance().findLatestCreatedTravel();
            if(this.travelToCreate.getNameTravel().equals(latest.getNameTravel())){
                res = 1;
            }

        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }

    int updateTravel(){
        int res = 0;
        try{
            this.createdTravel.setDescriptionTravel("MODIFIED_DESCRIPTION");
            TravelFacade.createInstance().updateTravel(this.createdTravel);
            Travel latest = TravelFacade.createInstance().findTravelById(this.createdTravel.getIdTravel());
            if(latest.getDescriptionTravel().equals(this.createdTravel.getDescriptionTravel())){
                res = 1;
            }

        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }

    int archiveTravel(){
        int res = 0;
        try{
            TravelFacade.createInstance().archiveTravel(this.createdTravel);
            Travel latest = TravelFacade.createInstance().findTravelById(this.createdTravel.getIdTravel());
            if(true == latest.isArchive()){
                res = 1;
            }

        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }


    int unarchiveTravel(){
        int res = 0;
        try{
            TravelFacade.createInstance().unarchiveTravel(this.createdTravel);
            Travel latest = TravelFacade.createInstance().findTravelById(this.createdTravel.getIdTravel());
            if(false == latest.isArchive()){
                res = 1;
            }

        }catch(DBNotFoundException e){
            res = 0;
        }
        return res;
    }

    int deleteTravel(){
        int res = 0;
        try{
            TravelFacade.createInstance().deleteTravel(this.createdTravel);
        }catch(DBNotFoundException e){
            res = 0;
        }
        try{
            Travel latest = TravelFacade.createInstance().findTravelById(this.createdTravel.getIdTravel());
        }catch(DBNotFoundException e ){
            System.out.println("It hasn't found the previous instance => has been deleted");
            res = 1;
        }
        return res;
    }




    @Test
    void testEverything(){
        System.out.println("Test lié à la méthode " + "createTravel() : " + this.createTravel() + "/1");
        System.out.println("Test lié à la méthode " + "findLatestCreatedTravel() : " + this.findLatestCreatedTravel() + "/1");
        System.out.println("Test lié à la méthode " + "findTravelById() : " + this.findTravelById() + "/1");
        System.out.println("Test lié à la méthode " + "updateTravel() : " + this.updateTravel() + "/1");
        System.out.println("Test lié à la méthode " + "archiveTravel() : " + this.archiveTravel() + "/1");
        System.out.println("Test lié à la méthode " + "unarchiveTravel() : " + this.unarchiveTravel() + "/1");
        System.out.println("Test lié à la méthode " + "deleteTravel() : " + this.deleteTravel() + "/1");


        //Récupérer le dernier Voyage et le comparer avec celui créer juste avant
        //Chercher le dernier voyage créer avec findByEmail()
        //Update en changeant le nom par un nouveau truc nul
        //Archiver le voyage
        //Le désarchiver
        //Le détruire
    }
}