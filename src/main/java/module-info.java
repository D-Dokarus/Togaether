module loginprototype {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires spring.security.core;

  exports loginprototype.BL.Facade;
  opens loginprototype.BL.Facade to javafx.fxml;
  exports loginprototype.UI.Frame;
  opens loginprototype.UI.Frame to javafx.fxml;
  exports loginprototype.UI.Controller;
  opens loginprototype.UI.Controller to javafx.fxml;
}