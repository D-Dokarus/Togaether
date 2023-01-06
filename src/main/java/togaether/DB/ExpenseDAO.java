package togaether.DB;

import togaether.BL.Model.Expense;

import java.sql.SQLException;
import java.util.List;

public interface ExpenseDAO {

  public Expense findExpenseById(int expense_id) throws SQLException;
  public void deleteExpenseById(int expense_id) throws SQLException;
  public void updateExpenseById(int expense_id) throws SQLException;
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException;
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException;
}
