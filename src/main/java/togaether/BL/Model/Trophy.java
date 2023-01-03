package togaether.BL.Model;

public class Trophy {
  private int id;
  private String name;
  private String category;
  private int category_id;
  private int value;
  private String image;

  public Trophy(int id, String name, String category, int category_id, int value, String image) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.category_id = category_id;
    this.value = value;
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getCategory_id() {
    return category_id;
  }

  public void setCategory_id(int category_id) {
    this.category_id = category_id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
