package togaether.DB;

import togaether.BL.Model.TrophyCategory;
import togaether.BL.Model.Trophy;

import java.sql.SQLException;
import java.util.List;

public interface TrophyDAO {
  /**
   * Create Trophy
   * @param name
   * @param category_id
   * @param value
   * @param image
   * @throws SQLException
   */
  public void createTrophy(String name, int category_id, int value, String image) throws SQLException;

  /**
   * Update Trophy
   * @param trophy
   * @throws SQLException
   */
  public void updateTrophy(Trophy trophy) throws SQLException;

  /**
   * Delete Trophy
   * @param trophy_id
   * @throws SQLException
   */
  public void deleteTrophyById(int trophy_id) throws SQLException;

  /**
   * Create an association between an user and a trophy, meaning that the user now owns the trophy
   * @param trophy_id
   * @param user_id
   * @throws SQLException
   */
  public void createTrophyUser(int trophy_id, int user_id) throws SQLException;

  /**
   * Return a List of all existing trophies
   * @return
   * @throws SQLException
   */
  public List<Trophy> findAllTrophies() throws SQLException;

  /**
   * Return a List of trophies of a TrophyCategory
   * @param category
   * @return
   * @throws SQLException
   */
  public List<Trophy> findTrophiesByCategories(String category) throws SQLException;

  /**
   * Return a List of trophies of a TrophyCategory that the specified user doesn't own
   * @param category
   * @param user_id
   * @return
   * @throws SQLException
   */
  public List<Trophy> findNotOwnedTrophiesByCategories(String category, int user_id) throws SQLException;

  /**
   * Return a List of all TrophyCategory
   * @return
   * @throws SQLException
   */
  public List<TrophyCategory> findAllCategories() throws SQLException;

  /**
   * Return a specific Trophy
   * @param trophy_id
   * @return
   * @throws SQLException
   */
  public Trophy findTrophyById(int trophy_id) throws SQLException;

  /**
   * Return a List of trophies owned by the user
   * @param user_id
   * @return
   * @throws SQLException
   */
  public List<Trophy> findTrophiesByUserId(int user_id) throws SQLException;

  /**
   * Count the travels created by the user
   * @param user_id
   * @return
   * @throws SQLException
   */
  public int countAllOwnedTravelsByUserId(int user_id) throws SQLException;

  /**
   * Count the number of travels where the user is participating
   * @param user_id
   * @return
   * @throws SQLException
   */
  public int countAllParticipatingTravelsByUserId(int user_id) throws SQLException;

  /**
   * Count the total of messages send by the user
   * @param user_id
   * @return
   * @throws SQLException
   */
  public int countMessageByUserId(int user_id) throws SQLException;

  /**
   * Count the maximum depense that an user did in a travel
   * @param user_id
   * @return
   * @throws SQLException
   */
  public int countMaxSumExpensesByUserId(int user_id) throws SQLException;

  /**
   * Count the total of the user's friends
   * @param user_id
   * @return
   * @throws SQLException
   */
  public int countFriendsByUserId(int user_id) throws SQLException;
}
