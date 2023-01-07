package togaether.BL.Model;

public class Collaborator {
  private int id;
  private Travel travel;
  private User user;
  private String name;

  public Collaborator(int id, Travel travel, User user, String name) {
    this.id = id;
    this.travel = travel;
    this.user = user;
    this.name = name;
  }

  public Collaborator( Travel travel, User user, String name) {
    this.travel = travel;
    this.user = user;
    this.name = name;
  }

  public Collaborator(int id, Travel travel, String name) {
    this.id = id;
    this.travel = travel;
    this.name = name;
  }

  public Collaborator(Travel travel, String name) {
    this.travel = travel;
    this.name = name;
  }
  public int getId() {
    return id;
  }

  public Travel getTravel() {
    return travel;
  }

  public void setTravel(Travel travel) {
    this.travel = travel;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "{id : " + getId()
            + "; Travel : " + getTravel()
            + "; Name : " + getName()
            + "; User : " + getUser() +"}";
  }
  @Override
  public boolean equals(Object o) {
    return this.id == ((Collaborator)o).getId();
  }
}
