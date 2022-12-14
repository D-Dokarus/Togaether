module loginprototype {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires spring.security.core;

  exports togaether.BL.Facade;
  opens togaether.BL.Facade to javafx.fxml;
  exports togaether.UI.Frame;
  opens togaether.UI.Frame to javafx.fxml;
  exports togaether.UI.Controller;
  opens togaether.UI.Controller to javafx.fxml;
}