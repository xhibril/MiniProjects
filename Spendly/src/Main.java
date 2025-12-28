import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controllers/Main.fxml"));
        BorderPane root = loader.load();   // store the root as borderpane

        stage.getIcons().add(new Image(getClass().getResourceAsStream("Images/Logo.png")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Spendly");
        scene.setFill(Color.web("#2D3436"));
        stage.show();





    }

    public static void main(String[] args) {
        launch();
    }
}