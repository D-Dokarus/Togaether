package togaether.BL.Facade;

import togaether.BL.Model.Collaborator;
import togaether.BL.Model.Expense;
import togaether.BL.Model.ExpenseCategory;
import togaether.BL.Model.Trophy;
import togaether.DB.AbstractFactory;
import togaether.DB.ExpenseDAO;
import togaether.DB.TravelDAO;
import togaether.DB.TrophyDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseFacade {
  static private final ExpenseFacade instance = new ExpenseFacade();

  private Expense expense = null;

  public static ExpenseFacade getInstance() {
    return instance;
  }

  /**
   * Return the actual Expense
   * @return
   */
  public Expense getExpense() {
    return expense;
  }

  /**
   * Set the actual Expense
   * @param expense
   */
  public void setExpense(Expense expense) {
    this.expense = expense;
  }

  /**
   * Create an Expense
   * @param travel_id
   * @param pay_master_id
   * @param expense_category_id
   * @param value
   * @param name
   * @param date
   * @throws SQLException
   */
  public int createExpense(int travel_id, int pay_master_id, int expense_category_id, double value, String name, Date date) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.createExpense(travel_id, pay_master_id, expense_category_id, value, name, date);
  }

  /**
   * Update an Expense
   * @param expense
   * @throws SQLException
   */
  public void updateExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.updateExpense(expense);
  }

  /**
   * Delete an Expense
   * @param expense
   * @throws SQLException
   */
  public void deleteExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.deleteExpenseById(expense.getId());
  }

  /**
   * Return a List of the Expenses of a Travel
   * @param travel_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByTravelId(travel_id);
  }

  /**
   * Return a List of the Expenses of an User
   * @param user_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByUserId(user_id);
  }

  /**
   * Return a List of the Expenses of a Collaborator
   * @param collaborator_id
   * @return
   * @throws SQLException
   */
  public List<Expense> findExpensesByCollaboratorId(int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByCollaboratorId(collaborator_id);
  }

  /**
   * Return all the ExpenseCategories
   * @return
   * @throws SQLException
   */
  public List<ExpenseCategory> findAllCategories() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findAllExpenseCategories();
  }

  /**
   * Return a List of the participant (Collaborator) of an Expense
   * @param expense
   * @return
   * @throws SQLException
   */
  public List<Collaborator> findParticipantsByExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findParticipantByExpenseId(expense.getId());
  }

  /**
   * Add a participant to an Expense
   * @param expense_id
   * @param collaborator_id
   * @throws SQLException
   */
  public void addParticipant(int expense_id, int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.createParticipant(expense_id, collaborator_id);
  }

  /**
   * Remove a participant of an Expense
   * @param expense_id
   * @param collaborator_id
   * @throws SQLException
   */
  public void removeParticipant(int expense_id, int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.deleteParticipant(expense_id, collaborator_id);
  }
  /**
   * Return the sum that a Collaborator has to paid in a travel
   * @param collaborator_id
   * @return
   * @throws SQLException
   */
  public double getSumExpenseToPaidByCollaboratorId(int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.calcAmountByCollaboratorId(collaborator_id);
  }

  /**
   * Return the sum that a Collaborator has to paid in a travel, by ExpenseCategory
   * @param collaborator_id
   * @param category_id
   * @return
   * @throws SQLException
   */
  public double getSumExpenseToPaidByCollaboratorIdAndCategoryId(int collaborator_id, int category_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.calcAmountByCollaboratorIdAndCategoryExpense(collaborator_id, category_id);
  }

  /**
   * Return the sum of all the expenses where the Collaborator is the pay master
   * @param collaborator_id
   * @return
   * @throws SQLException
   */
  public double getSumExpenseToGainByCollaboratorId(int collaborator_id) throws SQLException {
    ArrayList<Expense> expenses = (ArrayList<Expense>) findExpensesByCollaboratorId(collaborator_id);
    double sum = 0.0;
    for (Expense expense: expenses) {
      sum += expense.getValue();
    }
    return sum;
  }

  /**
   * Return the sum of all the expenses by collaborator and Category
   * @param collaborator_id
   * @param category
   * @return double
   * @throws SQLException
   */
  public double calcAmountByCollaboratorIdAndCategoryExpense(int collaborator_id, int category) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.calcAmountByCollaboratorIdAndCategoryExpense(collaborator_id, category);
  }

  /**
   * Return a String that represents the description of a ExpenseCategory name
   * @param name
   * @return
   */
  public String nameToFrench(String name) {
    if(name.equals("food"))
      return "Alimentation";
    else if(name.equals("transport"))
      return "Transport";
    else if(name.equals("other"))
      return "Autres";
    else if(name.equals("health"))
      return "Santé";
    else if(name.equals("accommodation"))
      return "Logement";
    else if(name.equals("entertainment"))
      return "Divertissement";
    return "nameToFrench de ExpenseFacade";
  }
  /**
   * Return a String that give the name of a ExpenseCategory from a description
   * @param french
   * @return
   */
  public String frenchToName(String french) {
    if(french.equals("Alimentation"))
      return "food";
    else if(french.equals("Transport"))
      return "transport";
    else if(french.equals("Autres"))
      return "other";
    else if(french.equals("Santé"))
      return "health";
    else if(french.equals("Logement"))
      return "accommodation";
    else if(french.equals("Divertissement"))
      return "entertainment";
    return "frenchToName de ExpenseFacade";
  }
}
