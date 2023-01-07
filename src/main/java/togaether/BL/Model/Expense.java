package togaether.BL.Model;

import java.sql.Date;

public class Expense {
  private int id;
  private int travel;
  private int pay_master;
  private ExpenseCategory category;
  private double value;
  private String name;
  private Date date;

  public Expense(int id, int travel, int pay_master, ExpenseCategory category, double value, String name, Date date) {
    this.id = id;
    this.travel = travel;
    this.pay_master = pay_master;
    this.category = category;
    this.value = value;
    this.name = name;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public int getTravel() {
    return travel;
  }

  public void setTravel(int travel) {
    this.travel = travel;
  }

  public int getPay_master() {
    return pay_master;
  }

  public void setPay_master(int pay_master) {
    this.pay_master = pay_master;
  }

  public ExpenseCategory getCategory() {
    return category;
  }

  public void setCategory(ExpenseCategory category) {
    this.category = category;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
