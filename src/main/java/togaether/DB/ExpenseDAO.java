package togaether.DB;

import togaether.BL.Model.Expense;
import togaether.BL.Model.ExpenseCategory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ExpenseDAO {

  public void createExpense(int travel_id, int pay_master_id, int expense_category_id, double expense_value, String expense_name, Date expense_date) throws SQLException;
  public void deleteExpenseById(int expense_id) throws SQLException;
  public void updateExpense(Expense expense) throws SQLException;
  public Expense findExpenseById(int expense_id) throws SQLException;
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException;
  public List<Expense> findExpensesByCollaboratorId(int collaborator_id) throws SQLException;
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException;
  public List<ExpenseCategory> findAllExpenseCategories() throws SQLException;
}
