package org.example.taskflow.utils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Redirect {

    public void redirect(Event event, String page){

        Node sourceNode = (Node) event.getSource();
        Scene currentScene = sourceNode.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/taskflow/" + page)
        );

        Scene scene = null;
        try {
            scene = new Scene(loader.load(), 1250, 750);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        currentStage.setScene(scene);
        currentStage.show();
    }
}
