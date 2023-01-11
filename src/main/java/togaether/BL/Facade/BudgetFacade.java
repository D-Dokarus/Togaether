package togaether.BL.Facade;

import togaether.BL.Model.Budget;
import togaether.BL.TogaetherException.DBNotFoundException;
import togaether.DB.AbstractFactory;
import togaether.DB.BudgetDAO;
import togaether.DB.ItineraryDAO;

import java.sql.SQLException;
import java.util.List;

public class BudgetFacade {

    static private BudgetFacade instance = new BudgetFacade();

    public static BudgetFacade getInstance() {
        return instance;
    }

    public void createBudget(Budget budget) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.createBudget(budget);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void deleteBudgetById(int Id) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.deleteBudgetById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public Budget findBudgetById(int Id) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            return budgetDAO.findBudgetById(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public double findLimitByBudget(int Id) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();
        try {
            return budgetDAO.findLimitByBudget(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public double findLimitByCollaboratorAndTravelAndCat(int collaborator, int travel, int category) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            return budgetDAO.findLimitByCollaboratorAndTravelAndCat(collaborator, travel, category);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public List<Budget> findBudgetSByCollaboratorId(int Id) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            return budgetDAO.findBudgetSByCollaboratorId(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void updateBudget(Budget budget) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.updateBudget(budget);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void updateLimitByCollaboratorAndTravelAndCategory(double limit, int collaborator, int travel, int category) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.updateLimitByCollaboratorAndTravelAndCategory(limit, collaborator, travel, category);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void updateLimitBudgetById(int Id, double limit) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.updateLimitBudgetById(Id, limit);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void updateLimitBudgetByIdAndCat(int Id, int cat, double limit) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.updateLimitBudgetByIdAndCat(Id, cat, limit);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void updateLimitBudgetByCollaboratorAndCat(int collaborator, int cat, double limit) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.updateLimitBudgetByCollaboratorAndCat(collaborator, cat, limit);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

    public void deleteBudgetByCollaboratorId(int Id) throws SQLException{
        AbstractFactory fact = AbstractFactory.createInstance();
        BudgetDAO budgetDAO = fact.getBudgetDAO();

        try {
            budgetDAO.deleteBudgetByCollaboratorId(Id);
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        }
    }

}
