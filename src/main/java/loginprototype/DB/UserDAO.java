package loginprototype.DB;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public interface UserDAO {

  public void insertUser(String user_name, String user_email, String password)  throws SQLException;
  public ResultSet findByName(String user_name) throws SQLException;
  public List<User> findByEmail(String user_email) throws SQLException;
  public void deleteUserByUser(int user_id)  throws SQLException;
  public ResultSet getAll() throws SQLException;
}