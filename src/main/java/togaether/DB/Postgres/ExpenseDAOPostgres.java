package togaether.DB.Postgres;

import togaether.BL.Model.Expense;
import togaether.BL.Model.Trophy;
import togaether.DB.ExpenseDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {
  PostgresFactory postgres;

  public ExpenseDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }

  @Override
  public Expense findExpenseById(int expense_id) throws SQLException {
    Expense expense = null;
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category=ec.expense_category_id WHERE e.expense_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, expense_id);
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            //expense = new Expense(resultSet.getString("trophy_name"), );
          }
        }
      }
    }
    return expense;
  }

  @Override
  public void deleteExpenseById(int expense_id) throws SQLException {

  }

  @Override
  public void updateExpenseById(int expense_id) throws SQLException {

  }

  @Override
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException {
    return null;
  }

  @Override
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException {
    return null;
  }
}
