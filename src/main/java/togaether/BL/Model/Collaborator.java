package togaether.BL.Model;

public class Collaborator {
  private int id;
  private int travel;
  private int user;
  private String name;

  public Collaborator(int id, int travel, int user, String name) {
    this.id = id;
    this.travel = travel;
    this.user = user;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTravel() {
    return travel;
  }

  public void setTravel(int travel) {
    this.travel = travel;
  }

  public int getUser() {
    return user;
  }

  public void setUser(int user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
