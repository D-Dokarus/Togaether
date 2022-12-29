package togaether.DB;

import togaether.BL.Model.User;

import java.sql.*;

import java.util.List;

/**
 * Interface décrivant les différentes actions d'un DAO concernant User
 */
public interface UserDAO {

    /**
     * Ajoute un User dans la DB
     * @param user_name
     * @param user_surname
     * @param user_pseudo
     * @param user_country
     * @param user_dateCreation
     * @param user_email
     * @param user_password
     * @throws SQLException
     */
    public void insertUser(String user_name, String user_surname, String user_pseudo, String user_country, String user_dateCreation, String user_email, String user_password) throws SQLException;
    public void insertUser(User u) throws SQLException;
  /**
   * Retourne le résultat d'une recherche d'utilisateur filtré par nom
   * @param user_name
   * @return
   * @throws SQLException
   */
  public List<User> findByName(String user_name) throws SQLException;

  /**
   * Retourne le résultat d'une recherche d'utilisateur filtré par mail
   *
   * @param user_email
   * @return
   * @throws SQLException
   */
  public User findByEmail(String user_email) throws SQLException;

  public User findByUserId(int idUser) throws SQLException;

  public User findByPseudo(String pseudo) throws SQLException;

  /**
   * Supprime un utilisateur par son id
   * @param user_id
   * @throws SQLException
   */
  public void deleteUserByUserId(int user_id)  throws SQLException;

  /**
   * Retourne toutes les données de User
   * @return
   * @throws SQLException
   */
  public List<User> getAll() throws SQLException;

}
