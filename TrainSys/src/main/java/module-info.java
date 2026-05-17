module org.example.trainsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires kernel;
    requires layout;


    opens org.example.trainsys to javafx.fxml;
    exports org.example.trainsys;
    exports org.example.trainsys.controller;
    opens org.example.trainsys.controller to javafx.fxml;
}