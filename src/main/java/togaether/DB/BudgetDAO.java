package togaether.DB;

import togaether.BL.Model.Budget;

import java.sql.SQLException;
import java.util.List;

public interface BudgetDAO {

    public void createBudget(Budget budget) throws SQLException;

    public void deleteBudgetById(int Id) throws SQLException;

    public Budget findBudgetById(int Id) throws SQLException;

    public double findLimitByBudget(int Id) throws SQLException;

    public double findLimitByCollaboratorAndTravelAndCat(int collaborator, int travel, int category) throws SQLException;

    public List<Budget> findBudgetSByCollaboratorId(int Id) throws SQLException;

    public void updateBudget(Budget budget) throws SQLException;

    public void updateLimitByCollaboratorAndTravelAndCategory(double limit, int collaborator, int travel, int category) throws SQLException;

    public void updateLimitBudgetById(int Id, double limit) throws SQLException;

    public void updateLimitBudgetByIdAndCat(int Id, int category, double limit) throws SQLException;

    public void updateLimitBudgetByCollaboratorAndCat(int collaborator, int category, double limit) throws SQLException;

    public void deleteBudgetByCollaboratorId(int Id) throws SQLException;
}
