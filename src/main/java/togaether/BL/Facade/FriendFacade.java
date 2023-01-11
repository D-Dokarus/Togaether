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

    /**
     * Query the creation of a Friend
     * @param friend
     * @return the friend newly created
     */
    public Friend createFriend(Friend friend){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();

        try{
            Friend fr = fdaop.createFriend(friend);
            TrophyFacade.getInstance().isTrophyValidForUser(friend.getUser1().getId(), "friend");
            TrophyFacade.getInstance().isTrophyValidForUser(friend.getUser2().getId(), "friend");
            return fr;
        } catch (SQLException e) {
            System.out.println("CREATION FAILED :");
            System.out.println(e);
        }
        return null;
    }

    /**
     * Fetch all friends that are friend with the user given in parameter
     * @param user
     * @return a list of Friend
     */
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

    /**
     * Query in the database the research of an existing Friend instance
     * If exists, return it, otherwise return null
     * @param you
     * @param other
     * @return a friend or null
     */
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

    /**
     * Query the suppression of the friend instance given in parameter
     * @param friend
     */
    public void deleteFriend(Friend friend){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        try{
            fdaop.deleteFriend(friend);
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Query the suppression of all users that are friends with the user given in parameter
     * @param user
     */
    public void deleteAllFriends(User user){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();
        try{
            fdaop.deleteAllFriends(user);
        }catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Query the list of all friends that are friend with the user given in parameter, and that contains the specific string in its pseudo.
     * @param user
     * @param string
     * @return a list of Friends
     */
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
