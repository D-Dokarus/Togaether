package togaether.DB;

import togaether.BL.Model.CategoryTrophy;
import togaether.BL.Model.Trophy;

import java.sql.SQLException;
import java.util.List;

public interface TrophyDAO {

  public void createTrophy(String name, int category_id, int value, String image) throws SQLException;
  public void updateTrophy(Trophy trophy) throws SQLException;
  public void deleteTrophyById(int trophy_id) throws SQLException;
  public void createTrophyUser(int trophy_id, int user_id) throws SQLException;
  public List<Trophy> findAllTrophies() throws SQLException;
  public List<CategoryTrophy> findAllCategories() throws SQLException;
  public Trophy findTrophyById(int trophy_id) throws SQLException;
  public List<Trophy> findTrophiesByUserId(int user_id) throws SQLException;
}
