import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.DBconnection;

public class Main extends Application {
    public void start(Stage stage) throws Exception {

        // connect to DB
        DBconnection.connectToDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Login.fxml"));
        System.out.println(getClass().getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/appIcon.png"))
        );

        stage.setTitle("Bookify");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}