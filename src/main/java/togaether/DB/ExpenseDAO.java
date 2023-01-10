package togaether.DB;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Expense;
import togaether.BL.Model.ExpenseCategory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ExpenseDAO {

  /**
   * Create an Expense in a Travel
   * @param travel_id
   * @param pay_master_id
   * @param expense_category_id
   * @param expense_value
   * @param expense_name
   * @param expense_date
   * @throws SQLException
   */
  public void createExpense(int travel_id, int pay_master_id, int expense_category_id, double expense_value, String expense_name, Date expense_date) throws SQLException;

  /**
   * Delete an Expense
   * @param expense_id
   * @throws SQLException
   */
  public void deleteExpenseById(int expense_id) throws SQLException;

  /**
   * Update an Expense
   * @param expense
   * @throws SQLException
   */
  public void updateExpense(Expense expense) throws SQLException;

  /**
   * Return a specific Expense
   * @param expense_id
   * @return
   * @throws SQLException
   */
  public Expense findExpenseById(int expense_id) throws SQLException;

  /**
   * Create a participant of an Expense
   * @param expense_id
   * @param collaborator_id
   * @throws SQLException
   */
  public void createParticipant(int expense_id, int collaborator_id) throws SQLException;

  /**
   * Remove a participant of an Expense
   * @param expense_id
   * @param collaborator_id
   * @throws SQLException
   */
  public void deleteParticipant(int expense_id, int collaborator_id) throws SQLException;

  /**
   * Return a List of all the participant (Collaborator) of an Expense
   * @param expense_id
   * @return
   * @throws SQLException
   */
  public List<Collaborator> findParticipantByExpenseId(int expense_id) throws SQLException;

  /**
   * Return the total amount that a Collaborator has to paid
   * @param collaborator_id
   * @return
   * @throws SQLException
   */
  public double calcAmountByCollaboratorId(int collaborator_id) throws SQLException;

  /**
   * Return the total amount that a Collaborator has to paid for a specific ExpenseCategory
   * @param collaborator_id
   * @param category_id
   * @return
   * @throws SQLException
   */
  public double calcAmountByCollaboratorIdAndCategoryExpense(int collaborator_id, int category_id) throws SQLException;

  /**
   * Return a List of all the Expense of a Travel
   * @param travel_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException;

  /**
   * Return a List of all the Expense of a Collaborator
   * @param collaborator_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByCollaboratorId(int collaborator_id) throws SQLException;

  /**
   * Return a List of the Expense of an User
   * @param user_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException;

  /**
   * Return a List of all existing ExpenseCategory
   * @return
   * @throws SQLException
   */
  public List<ExpenseCategory> findAllExpenseCategories() throws SQLException;
}
