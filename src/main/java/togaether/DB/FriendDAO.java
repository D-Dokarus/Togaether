package togaether.DB;

import togaether.BL.Model.Friend;
import togaether.BL.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface FriendDAO {

    public Friend createFriend(Friend friend) throws SQLException;
    public Friend isAlreadyFriendWith(User user1, User user2) throws SQLException;
    public List<Friend> findAllFriends(User user) throws SQLException;
    public void deleteFriend(Friend friend) throws SQLException;
    public void deleteAllFriends(User user) throws SQLException;
}
