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
import java.util.List;

public class ExpenseFacade {
  static private final ExpenseFacade instance = new ExpenseFacade();

  private Expense expense = null;

  public static ExpenseFacade getInstance() {
    return instance;
  }

  public Expense getExpense() {
    return expense;
  }

  public void setExpense(Expense expense) {
    this.expense = expense;
  }

  public void createExpense(int travel_id, int pay_master_id, int expense_category_id, double value, String name, Date date) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.createExpense(travel_id, pay_master_id, expense_category_id, value, name, date);
  }

  public void updateExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.updateExpense(expense);
  }
  public void deleteExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.deleteExpenseById(expense.getId());
  }
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByTravelId(travel_id);
  }
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByUserId(user_id);
  }
  public List<Expense> findExpensesByCollaboratorId() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findExpensesByUserId(TravelFacade.getInstance().getCollaborator().getId());
  }
  public List<ExpenseCategory> findAllCategories() throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findAllExpenseCategories();
  }
  public List<Collaborator> findParticipantsByExpense(Expense expense) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    return expenseDB.findParticipantByExpenseId(expense.getId());
  }
  public void addParticipant(int expense_id, int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.createParticipant(expense_id, collaborator_id);
  }
  public void removeParticipant(int expense_id, int collaborator_id) throws SQLException {
    AbstractFactory abstractFactory = AbstractFactory.createInstance();
    ExpenseDAO expenseDB = abstractFactory.getExpenseDAO();
    expenseDB.deleteParticipant(expense_id, collaborator_id);
  }

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
