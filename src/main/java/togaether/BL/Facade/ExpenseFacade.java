package togaether.BL.Facade;

import togaether.BL.Model.Expense;

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
}
