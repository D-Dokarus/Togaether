module loginprototype {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires spring.security.core;

  opens loginprototype.UI to javafx.fxml;
  exports loginprototype.UI;
  opens loginprototype.BL to javafx.fxml;
  exports loginprototype.BL;
}