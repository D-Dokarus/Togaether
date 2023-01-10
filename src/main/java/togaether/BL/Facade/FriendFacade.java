package togaether.BL.Facade;

import togaether.BL.Model.Friend;
import togaether.BL.Model.User;
import togaether.DB.AbstractFactory;
import togaether.DB.FriendDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class FriendFacade {

    private static FriendFacade instance = new FriendFacade();
    public static FriendFacade getInstance(){return instance;}

    public Friend createFriend(Friend friend){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();

        try{
            Friend fr = fdaop.createFriend(friend);
            return fr;
        } catch (SQLException e) {
            System.out.println("CREATION FAILED :");
            System.out.println(e);
        }
        return null;
    }

    public List<Friend> findAllFriends(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        List<Friend> friends = new ArrayList<>();

        try{

            friends = fdaop.findAllFriends(user);
        }catch (SQLException e) {
            System.out.println(e);
        }
        return friends;
    }

    public Friend isAlreadyFriendWith(User you, User other){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        Friend toReturn = null;

        try{
            toReturn = fdaop.isAlreadyFriendWith(you,other);
        }catch (SQLException e) {
            System.out.println(e);
        }
        return toReturn;
    }

    public void deleteFriend(Friend friend){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        try{
            fdaop.deleteFriend(friend);
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteAllFriends(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        try{
            fdaop.deleteAllFriends(user);
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Friend> findAllFriendsByPseudo(User user,String string){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        List<Friend> friends = new ArrayList<>();
        try{
            friends = fdaop.findAllFriendsByPseudo(user,string);
        }catch (SQLException e) {
            System.out.println(e);
        }
        return friends;
    }
}
