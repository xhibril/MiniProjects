package org.example.trainsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AuthPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        stage.setTitle("TrainSys");
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/org/example/trainsys/images/standardIcon.png"))
        );
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
