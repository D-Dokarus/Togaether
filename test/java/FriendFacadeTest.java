import org.junit.jupiter.api.Test;
import togaether.BL.Facade.FriendFacade;
import togaether.BL.Facade.UserFacade;
import togaether.BL.Model.Friend;
import togaether.BL.Model.User;
import togaether.BL.TogaetherException.*;

import java.util.List;

public class FriendFacadeTest {

    @Test
    public void isAlreadyFriendWith(){

        String name1 = "TEST";
        String name2 = "TEST2";
        /*
        try{
            UserFacade.getInstance().register(name1,"",name1,"a@gmail.com","mdpazertya","mdpazertya","France");
            UserFacade.getInstance().register(name2,"",name2,"b@gmail.com","mdpazertya","mdpazertya","France");
        } catch (UserBadEmailException e) {
            throw new RuntimeException(e);
        } catch (UserAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (DBNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserBadConfirmPasswordException e) {
            throw new RuntimeException(e);
        } catch (UserBadPasswordException e) {
            throw new RuntimeException(e);
        } catch (UserPseudoAlreadyExistException e) {
            throw new RuntimeException(e);
        }

         */

        List<User> lu = UserFacade.getInstance().findAllUsersByPseudo(name1);
        List<User> lu2 = UserFacade.getInstance().findAllUsersByPseudo(name2);


        User u1 = lu.get(0);
        User u2 = lu2.get(0);

        System.out.println(u1.getName());
        System.out.println(u2.getName());
        System.out.println("Cette fois, on test si une amitié existe");
        Friend f = FriendFacade.getInstance().isAlreadyFriendWith(u1,u2);
        if(f == null){
            System.out.println("Les deux users que l'on vient de créer NE SONT PAS amis");
        }
        FriendFacade.getInstance().createFriend(new Friend(u1,u2));
        Friend f2 = FriendFacade.getInstance().isAlreadyFriendWith(u1,u2);

        System.out.println("Cette fois ci on test si une amitié existe");
        if(f2 != null){
            System.out.println("Les deux users que l'on vient de créer SONT amis");
        }

        System.out.println("On efface les users créés et la nouvelle amitié");
        try{
            FriendFacade.getInstance().deleteFriend(f2);
            UserFacade.getInstance().deleteAccount(u1.getName());
            UserFacade.getInstance().deleteAccount(u2.getName());
        } catch (DBNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UserBadPasswordException e) {
            throw new RuntimeException(e);
        }


    }
}
