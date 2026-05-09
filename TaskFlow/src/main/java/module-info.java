module org.example.taskflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens org.example.taskflow to javafx.fxml;
    opens org.example.taskflow.controller to javafx.fxml;

    exports org.example.taskflow;
}