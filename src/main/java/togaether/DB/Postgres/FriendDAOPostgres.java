package togaether.DB.Postgres;

import togaether.BL.Model.Friend;
import togaether.BL.Model.User;
import togaether.DB.AbstractFactory;
import togaether.DB.CollaboratorDAO;
import togaether.DB.FriendDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendDAOPostgres implements FriendDAO {

    PostgresFactory postgres;

    public FriendDAOPostgres(PostgresFactory postgres) { this.postgres = postgres;}

    @Override
    public Friend createFriend(Friend friend) throws SQLException {

        try(Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO friend(user_id_1,user_id_2) VALUES(?,?) RETURNING user_id_1,user_id_2,beginning_of_friendship;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,friend.getUser1().getId());
                statement.setInt(2,friend.getUser2().getId());
                statement.execute();
                ResultSet result = statement.getResultSet();
                result.next();

                Friend toReturn = new Friend(friend.getUser1(),friend.getUser2(),result.getDate("beginning_of_friendship"));
                return toReturn;
            }
        }
    }

    @Override
    public Friend isAlreadyFriendWith(User user1, User user2) throws SQLException {
        Friend friend = null;
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT u1.user_id as u1_id, u2.user_id as u2_id, u1.user_name as u1_name, u2.user_name as u2_name, u1.user_surname as u1_surname, u2.user_surname as u2_surname, u1.user_pseudo as u1_pseudo, u2.user_pseudo as u2_pseudo, beginning_of_friendship FROM friend, public.user as u1, public.user as u2 WHERE u1.user_id = friend.user_id_1 AND u2.user_id = friend.user_id_2 AND ((u1.user_id = ? AND u2.user_id = ?) OR (u1.user_id = ? AND u2.user_id = ?));";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,user1.getId());
                statement.setInt(2,user2.getId());
                statement.setInt(3,user2.getId());
                statement.setInt(4,user1.getId());
                statement.executeQuery();
                ResultSet result = statement.getResultSet();
                if(result.next()){
                    User u1 = new User(result.getInt("u1_id"),result.getString("u1_name"),result.getString("u1_surname"),result.getString("u1_pseudo"));
                    User u2 = new User(result.getInt("u2_id"),result.getString("u2_name"),result.getString("u2_surname"),result.getString("u2_pseudo"));
                    friend = new Friend(u1,u2,result.getDate("beginning_of_friendship"));
                    return friend;
                }
            }
        }
        return friend;
    }

    @Override
    public List<Friend> findAllFriends(User user) throws SQLException {
        List<Friend> friends = new ArrayList<>();
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT u1.user_id as u1_id, u2.user_id as u2_id, u1.user_name as u1_name, u2.user_name as u2_name, u1.user_surname as u1_surname, u2.user_surname as u2_surname, u1.user_pseudo as u1_pseudo, u2.user_pseudo as u2_pseudo,u1.user_country as u1_country,u2.user_country as u2_country, beginning_of_friendship FROM friend, public.user as u1, public.user as u2 WHERE u1.user_id = friend.user_id_1 AND u2.user_id = friend.user_id_2 AND (u1.user_id = ? OR u2.user_id = ?)";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,user.getId());
                statement.setInt(2,user.getId());
                statement.executeQuery();
                ResultSet result = statement.getResultSet();

                while(result.next()){
                    User u1 = new User(result.getInt("u1_id"),result.getString("u1_name"),result.getString("u1_surname"),result.getString("u1_pseudo"),result.getString("u1_country"));
                    User u2 = new User(result.getInt("u2_id"),result.getString("u2_name"),result.getString("u2_surname"),result.getString("u2_pseudo"),result.getString("u2_country"));
                    Friend friend = new Friend(u1,u2,result.getDate("beginning_of_friendship"));
                    friends.add(friend);
                }
            }
        }
        return friends;
    }

    @Override
    public void deleteFriend(Friend friend) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM friend WHERE (user_id_1 = ? AND user_id_2 = ?) OR (user_id_1 = ? AND user_id_2 = ?);";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,friend.getUser1().getId());
                statement.setInt(2,friend.getUser2().getId());
                statement.setInt(3,friend.getUser2().getId());
                statement.setInt(4,friend.getUser1().getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void deleteAllFriends(User user) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM friend WHERE user_id_1 = ? OR user_id_2 = ?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,user.getId());
                statement.setInt(2,user.getId());
                statement.executeUpdate();
            }
        }
    }
    public List<Friend> findAllFriendsByPseudo(User user, String string) throws SQLException{
        List<Friend> friends = new ArrayList<>();
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT u1.user_id as u1_id, u2.user_id as u2_id, u1.user_name as u1_name, u2.user_name as u2_name, u1.user_surname as u1_surname, u2.user_surname as u2_surname, u1.user_pseudo as u1_pseudo, u2.user_pseudo as u2_pseudo, u1.user_country as u2_country,u1.user_country as u1_country, beginning_of_friendship FROM friend, public.user as u1, public.user as u2 WHERE u1.user_id = friend.user_id_1 AND u2.user_id = friend.user_id_2 AND ((u1.user_id = ? AND u2.user_pseudo like ?) OR (u2.user_id = ? AND u1.user_pseudo like ?));";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,user.getId());
                statement.setString(2,string);
                statement.setInt(3,user.getId());
                statement.setString(4,string);
                statement.executeQuery();
                ResultSet result = statement.getResultSet();

                while(result.next()){
                    User u1 = new User(result.getInt("u1_id"),result.getString("u1_name"),result.getString("u1_surname"),result.getString("u1_pseudo"),result.getString("u1_country"));
                    User u2 = new User(result.getInt("u2_id"),result.getString("u2_name"),result.getString("u2_surname"),result.getString("u2_pseudo"),result.getString("u2_country"));
                    Friend friend = new Friend(u1,u2,result.getDate("beginning_of_friendship"));
                    friends.add(friend);
                }
            }
        }
        return friends;
    }

    public static void main(String args[]){
        AbstractFactory af = AbstractFactory.createInstance();
        FriendDAO fdaop = af.getFriendDAO();


        //ON TEST LE CREATE
        /*
        try{
            User u1 = new User(2,"","","");
            Friend f = new Friend(u1,u1);
            Friend fr = fdaop.createFriend(f);
            System.out.println(fr.getBeginningOfFriendship());
        }catch (SQLException e) {
            System.out.println(e);
        }*/

        //ON TEST LE isAlreadyFriend
        /*
        try{
            User u1 = new User(2,"","","");
            User u2 = new User(4,"","","");
            Friend fr = fdaop.isAlreadyFriendWith(u1,u2);
            System.out.println(fr.getBeginningOfFriendship());
        }catch (SQLException e) {
            System.out.println(e);
        }
         */

        //ON TEST LE findAllFriends()
        /*
        try{
            User u1 = new User(4,"","","");
            List<Friend> friends = fdaop.findAllFriends(u1);
            for(Friend f : friends){
                System.out.println(f.getBeginningOfFriendship());
            }

        }catch (SQLException e) {
            System.out.println(e);
        }*/
        //ON test le deleteFriend()
        /*
        try{
            User u1 = new User(4,"","","");
            User u2 = new User(2,"","","");
            Friend f = new Friend(u1,u2);
            fdaop.deleteFriend(f);
            Friend res = fdaop.isAlreadyFriendWith(u1,u2);
            if(res == null){
                System.out.println("friendship anhihilate !");
            }

        }catch (SQLException e) {
            System.out.println(e);
        }*/

        //On test le delete allFriends()
        /*
        try{
            User u1 = new User(4,"","","");
            fdaop.deleteAllFriends(u1);
        }catch (SQLException e) {
            System.out.println(e);
        }*/

    }

}


