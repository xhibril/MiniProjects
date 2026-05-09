package org.example.taskflow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AuthPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 750);
        stage.setTitle("TaskFlow");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/org/example/taskflow/images/icon.png"))
        );
        stage.show();
    }
}
