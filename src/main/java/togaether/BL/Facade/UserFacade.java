package togaether.BL.Facade;

import togaether.BL.Model.User;
import togaether.BL.TogaetherException.*;
import togaether.DB.AbstractFactory;
import togaether.DB.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe singleton regroupant différentes foncionnalité lié à l'utilisateur courant
 */
public class UserFacade {

    private User connectedUser = null;

    static private UserFacade instance = new UserFacade();

    /**
     * Méthode vérifiant les identifiants données par l'utilisateur. Si les identifiants ne correspondent pas, ou si une erreur se produit, une exception est levée
     *
     * @param email
     * @param password
     * @throws UserNotFoundException
     * @throws UserBadPasswordException
     * @throws DBNotFoundException
     */
    public void login(String email, String password) throws UserNotFoundException, UserBadPasswordException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            User u = userDB.findByEmail(email);
            if (u != null) {
                if (BCrypt.checkpw(password, u.getPassword())) {
                    this.connectedUser = u;
                } else
                    throw new UserBadPasswordException();
            } else
                throw new UserNotFoundException();

        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de récupérer l'utilisateur courant
     */
    public User getConnectedUser() {
        return connectedUser;
    }

    /**
     * Méthode permettant d'enregistrer un nouvel utilisateur
     * @param name
     * @param surname
     * @param pseudo
     * @param email
     * @param password
     * @param confirmPassword
     * @param country
     * @throws DBNotFoundException
     * @throws UserAlreadyExistException
     * @throws UserBadPasswordException
     * @throws UserBadConfirmPasswordException
     * @throws UserBadEmailException
     */
    public void register(String name, String surname, String pseudo, String email, String password, String confirmPassword, String country) throws UserBadPasswordException, UserBadConfirmPasswordException, UserAlreadyExistException, UserPseudoAlreadyExistException, DBNotFoundException, UserBadEmailException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        if (!password.equals(confirmPassword))
            throw new UserBadConfirmPasswordException();

        if (password.length() < 8)
            throw new UserBadPasswordException();

        //Vérifie si c'est un email valide
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$"))
            throw new UserBadEmailException();

        try {
            if (userDB.findByEmail(email) != null)
                throw new UserAlreadyExistException();

            if (userDB.findByPseudo(pseudo) != null)
                throw new UserPseudoAlreadyExistException();

            User u = new User(name, surname, pseudo, email, BCrypt.hashpw(password, BCrypt.gensalt()), country);
            userDB.insertUser(u);
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de supprimer un utilisateur
     * @param password
     * @throws DBNotFoundException
     * @throws UserBadPasswordException
     */
    public void deleteAccount(String password) throws UserBadPasswordException, DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        if (!BCrypt.checkpw(password, connectedUser.getPassword()))
            throw new UserBadPasswordException();

        try {
            userDB.deleteUserByUserId(connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de modifier les informations d'un utilisateur
     * @param name
     * @param surname
     * @param pseudo
     * @param email
     * @param password
     * @param confirmPassword
     * @param country
     * @throws DBNotFoundException
     * @throws UserAlreadyExistException
     * @throws UserBadPasswordException
     * @throws UserBadConfirmPasswordException
     * @throws UserBadEmailException
     */
    public void updateAccount(String name, String surname, String pseudo, String email, String password, String confirmPassword, String country, int userId) throws UserBadPasswordException, UserBadConfirmPasswordException, UserAlreadyExistException, UserPseudoAlreadyExistException, DBNotFoundException, UserBadEmailException, SQLException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        if (!password.equals(confirmPassword))
            throw new UserBadConfirmPasswordException();

        if (password.length() < 8)
            throw new UserBadPasswordException();

        //Vérifie si c'est un email valide
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            System.out.println(email);
            System.out.println("aa");
            throw new UserBadEmailException();

        }
        try {
            if (userDB.findByEmail(email) != null && userDB.findByEmail(email).getId() != userId) {
                System.out.println("bb");
                throw new UserAlreadyExistException();

            }

            if (userDB.findByPseudo(pseudo) != null && userDB.findByPseudo(pseudo).getId() != userId) {
                throw new UserPseudoAlreadyExistException();
            }
            User u = new User(name, surname, pseudo, email, BCrypt.hashpw(password, BCrypt.gensalt()), country);
            userDB.updateAccount(u, userId);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        } catch (
                UserAlreadyExistException e) {
            throw new UserAlreadyExistException();
        } catch (
                UserPseudoAlreadyExistException e) {
            throw new UserPseudoAlreadyExistException();
        }

    }

    /**
     * Méthode permettant de modifier le prénom d'un utilisateur
     * @param name
     * @throws DBNotFoundException
     */
    public void updateName(String name) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            userDB.updateName(name, connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de modifier le nom d'un utilisateur
     * @param surname
     * @throws DBNotFoundException
     */
    public void updateSurname(String surname) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            userDB.updateSurname(surname, connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de modifier le pseudo d'un utilisateur
     * @param pseudo
     * @throws DBNotFoundException
     */
    public void updatePseudo(String pseudo) throws DBNotFoundException, UserPseudoAlreadyExistException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            if (userDB.findByPseudo(pseudo) != null)
                throw new UserPseudoAlreadyExistException();

            userDB.updatePseudo(pseudo, connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }

    }

    /**
     * Méthode permettant de modifier l'email d'un utilisateur
     * @param email
     * @throws DBNotFoundException
     */
    public void updateEmail(String email) throws DBNotFoundException, UserAlreadyExistException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            if (userDB.findByEmail(email) != null)
                throw new UserAlreadyExistException();

            userDB.updateEmail(email, connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de modifier le mot de passe d'un utilisateur
     * @param password
     * @throws DBNotFoundException
     */
    public void updatePassword(String password, String confirmPassword) throws DBNotFoundException, UserBadConfirmPasswordException, UserBadPasswordException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        if (!password.equals(confirmPassword))
            throw new UserBadConfirmPasswordException();

        if (password.length() < 8)
            throw new UserBadPasswordException();

        try {
            userDB.updatePassword(BCrypt.hashpw(password, BCrypt.gensalt()), connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de modifier le pays d'un utilisateur
     * @param country
     * @throws DBNotFoundException
     */
    public void updateCountry(String country) throws DBNotFoundException {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        try {
            userDB.updateCountry(country, connectedUser.getId());
        } catch (SQLException e) {
            throw new DBNotFoundException();
        }
    }

    /**
     * Méthode permettant de deconnecter un utilisateur
     */
    public void logout() {
        connectedUser = null;
    }


    /**
     * Méthode permettant de récupérer tous les utilisateurs par leur pseudo
     * @param pseudo
     * @return List<User>
     * @throws DBNotFoundException
     * @throws SQLException
     */
    public List<User> findAllUsersByPseudo(String pseudo) {
        AbstractFactory fact = AbstractFactory.createInstance();
        UserDAO userDB = fact.getUserDAO();

        List<User> users = new ArrayList<>();
        try {
            users = userDB.findAllUsersByPseudo(pseudo);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }


    public static UserFacade getInstance() {
        return instance;
    }

}
