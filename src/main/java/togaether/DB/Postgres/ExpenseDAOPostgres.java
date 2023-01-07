package togaether.DB.Postgres;

import togaether.BL.Model.Expense;
import togaether.BL.Model.ExpenseCategory;
import togaether.DB.ExpenseDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {
  PostgresFactory postgres;

  public ExpenseDAOPostgres(PostgresFactory postgres) {
    this.postgres = postgres;
  }
  @Override
  public void createExpense(int travel_id, int pay_master_id, int expense_category_id, double expense_value, String expense_name, Date expense_date) throws SQLException {
    try (Connection connection = this.postgres.getConnection()){
      String query = "INSERT INTO expense(travel, pay_master, expense_category, expense_value, expense_name, expense_date) VALUES(?,?,?,?,?,?);";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, travel_id);
        statement.setInt(2, pay_master_id);
        statement.setInt(3, expense_category_id);
        statement.setDouble(4, expense_value);
        statement.setString(5, expense_name);
        statement.setDate(6, expense_date);

        statement.executeUpdate();
      }
    }
  }

  @Override
  public void updateExpense(Expense expense) throws SQLException {
    try(Connection connection = this.postgres.getConnection()) {
      String query = "UPDATE expense SET travel = ? , pay_master = ? , expense_category = ? , expense_value = ? , expense_name = ? , expense_date = ? WHERE expense_id = ? ;";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(7, expense.getId());
        statement.setInt(1, expense.getTravel());
        statement.setInt(2, expense.getPay_master());
        statement.setInt(3, expense.getCategory().getId());
        statement.setDouble(4, expense.getValue());
        statement.setString(5, expense.getName());
        statement.setDate(6, expense.getDate());

        statement.executeUpdate();
      }
    }
  }
  @Override
  public void deleteExpenseById(int expense_id) throws SQLException {
    try(Connection connection = this.postgres.getConnection()){
      String query = "DELETE FROM expense WHERE expense_id=?;";
      try(PreparedStatement statement = connection.prepareStatement(query)){
        statement.setInt(1,expense_id);
        statement.executeUpdate();
      }
    }
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
            expense = new Expense(resultSet.getInt("expense_id"), resultSet.getInt("travel"),resultSet.getInt("pay_master"), new ExpenseCategory(resultSet.getInt("expense_category_id"), resultSet.getString("expense_category_name")),resultSet.getDouble("expense_value"),resultSet.getString("expense_name"),resultSet.getDate("expense_date"));
          }
        }
      }
    }
    return expense;
  }


  @Override
  public List<Expense> findExpensesByTravelId(int travel_id) throws SQLException {
    ArrayList<Expense> liste = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category=ec.expense_category_id WHERE e.travel =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, travel_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            liste.add(new Expense(resultSet.getInt("expense_id"), resultSet.getInt("travel"),resultSet.getInt("pay_master"), new ExpenseCategory(resultSet.getInt("expense_category_id"), resultSet.getString("expense_category_name")),resultSet.getDouble("expense_value"),resultSet.getString("expense_name"),resultSet.getDate("expense_date")));
          }
        }
      }
    }
    return liste;
  }

  public List<Expense> findExpensesByCollaboratorId(int collaborator_id) throws SQLException {
    ArrayList<Expense> liste = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category=ec.expense_category_id WHERE e.pay_master =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, collaborator_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            liste.add(new Expense(resultSet.getInt("expense_id"), resultSet.getInt("travel"),resultSet.getInt("pay_master"), new ExpenseCategory(resultSet.getInt("expense_category_id"), resultSet.getString("expense_category_name")),resultSet.getDouble("expense_value"),resultSet.getString("expense_name"),resultSet.getDate("expense_date")));
          }
        }
      }
    }
    return liste;
  }
  @Override
  public List<Expense> findExpensesByUserId(int user_id) throws SQLException {
    ArrayList<Expense> liste = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM expense e INNER JOIN expense_category ec ON e.expense_category=ec.expense_category_id INNER JOIN collaborator c ON e.pay_master=c.collaborator_id INNER JOIN public.user ON c.user_id = u.user_id WHERE c.user_id =?;";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        statement.setInt(1, user_id);
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            liste.add(new Expense(resultSet.getInt("expense_id"), resultSet.getInt("travel"),resultSet.getInt("pay_master"), new ExpenseCategory(resultSet.getInt("expense_category_id"), resultSet.getString("expense_category_name")),resultSet.getDouble("expense_value"),resultSet.getString("expense_name"),resultSet.getDate("expense_date")));
          }
        }
      }
    }
    return liste;
  }

  @Override
  public List<ExpenseCategory> findAllExpenseCategories() throws SQLException {
    ArrayList<ExpenseCategory> liste = new ArrayList<>();
    try (Connection connection = this.postgres.getConnection()){
      String query = "SELECT * FROM expense_category";
      try(PreparedStatement statement = connection.prepareStatement(query);){
        try (ResultSet resultSet = statement.executeQuery()) {

          while (resultSet.next()) {
            liste.add(new ExpenseCategory(resultSet.getInt("expense_category_id"), resultSet.getString("expense_category_name")));
          }
        }
      }
    }
    return liste;
  }

}
