package togaether.DB.Postgres;

import togaether.BL.Model.Budget;
import togaether.DB.BudgetDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAOPostgres implements BudgetDAO {

    PostgresFactory postgres;

    public BudgetDAOPostgres(PostgresFactory postgres) {
        this.postgres = postgres;
    }


    @Override
    public void createBudget(Budget budget) throws SQLException {
        try (Connection connection = this.postgres.getConnection()){
            String query = "INSERT INTO budget(travel, category, collaborator, limit_budget) VALUES (?,?,?,?);";
            try(PreparedStatement statement = connection.prepareStatement(query);){
                statement.setInt(1,budget.getTravel());
                statement.setInt(2,budget.getCategory());
                statement.setInt(3, budget.getCollaborator());
                statement.setDouble(4,budget.getLimit());
                statement.executeUpdate();
            }
        }
        finally {
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteBudgetById(int Id) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM budget WHERE budget_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void deleteBudgetByCollaboratorId(int Id) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "DELETE FROM budget WHERE collaborator=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                statement.executeUpdate();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public Budget findBudgetById(int Id) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM budget WHERE budget_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    Budget budget = new Budget(resultSet.getInt("budget_id"),
                            resultSet.getInt("travel"),
                            resultSet.getInt("category"),
                            resultSet.getInt("collaborator"),
                            resultSet.getDouble("limit_budget"));
                    return budget;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public double findLimitByBudget(int Id) throws SQLException{
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT limit_budget FROM budget WHERE budget_id=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    double limit_budget = resultSet.getDouble("limit_budget");
                    return limit_budget;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public double findLimitByCollaboratorAndTravelAndCat(int collaborator, int travel, int category) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT limit_budget FROM budget WHERE collaborator=? AND travel=? AND category=?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,collaborator);
                statement.setInt(2,travel);
                statement.setInt(3,category);
                try(ResultSet resultSet = statement.executeQuery()){
                    resultSet.next();
                    double limit_budget = resultSet.getDouble("limit_budget");
                    return limit_budget;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public List<Budget> findBudgetSByCollaboratorId(int Id) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "SELECT * FROM budget WHERE collaborator=? ORDER BY (category);";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,Id);
                try(ResultSet resultSet = statement.executeQuery()){

                    ArrayList<Budget> budgets = new ArrayList<>();
                    while(resultSet.next()){
                        Budget budget = new Budget(resultSet.getInt("budget_id"),
                                resultSet.getInt("travel"),
                                resultSet.getInt("category"),
                                resultSet.getInt("collaborator"),
                                resultSet.getDouble("limit_budget"));

                        budgets.add(budget);
                    }
                    return budgets;
                }
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateBudget(Budget budget) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE budget SET travel = ?,category = ?,collaborator = ?, limit_budget = ? WHERE budget_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setInt(1,budget.getTravel());
                statement.setInt(2,budget.getCategory());
                statement.setInt(3, budget.getCollaborator());
                statement.setDouble(4,budget.getLimit());
                statement.setDouble(5,budget.getBudget_id());
                statement.execute();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateLimitByCollaboratorAndTravelAndCategory(double limit_budget, int collaborator, int travel, int category) throws SQLException {
        System.out.println("Dans DAO");
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE budget SET limit_budget = ? WHERE collaborator = ? AND travel =? AND category = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDouble(1,limit_budget);
                statement.setInt(2,collaborator);
                statement.setInt(3,travel);
                statement.setInt(4,category);
                statement.execute();
                System.out.println(statement);
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateLimitBudgetById(int Id, double limit_budget) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE budget SET limit_budget = ? WHERE budget_id = ? ;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDouble(1,limit_budget);
                statement.setInt(2,Id);
                statement.execute();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateLimitBudgetByIdAndCat(int Id, int category, double limit_budget) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE budget SET limit_budget = ? WHERE budget_id = ? AND category = ?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDouble(1,limit_budget);
                statement.setInt(2,Id);
                statement.setInt(2,category);
                statement.execute();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }

    @Override
    public void updateLimitBudgetByCollaboratorAndCat(int collaborator, int category, double limit_budget) throws SQLException {
        try(Connection connection = this.postgres.getConnection()){
            String query = "UPDATE budget SET limit_budget = ? WHERE collaborator = ? AND category = ?;";
            try(PreparedStatement statement = connection.prepareStatement(query)){
                statement.setDouble(1,limit_budget);
                statement.setInt(2,collaborator);
                statement.setInt(2,category);
                statement.execute();
            }
        }
        finally{
            this.postgres.getConnection().close();
        }
    }


}
