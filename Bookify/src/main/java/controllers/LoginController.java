package controllers;
import services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Session;
import model.User;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    // ref to scene controller
    private SceneManager sceneManager;
    private User user;
    private Services services;

    String username, password;

    @FXML private TextField loginUsernameField;
    @FXML private TextField loginPasswordField;
    @FXML private Hyperlink goToSignUpPage;

    @FXML public void initialize() {
        services = new Services();
        sceneManager = new SceneManager();
        setGoToSignUpPage();
        setLoginPasswordField();
    }

    private void setGoToSignUpPage() {
        goToSignUpPage.setOnAction(e -> {
            sceneManager.setLoginController(this);

            // get the current stage
            Stage stage = (Stage) goToSignUpPage.getScene().getWindow();
            try {
                sceneManager.switchScenes(stage, "Signup.fxml");
            } catch (IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }

    private void setLoginPasswordField() {
        loginPasswordField.setOnAction(e -> {
            username = loginUsernameField.getText();
            password = loginPasswordField.getText();

            boolean areFieldsFilled = services.areFieldsFilled(username, password);
            user = new User(username, password, "USER");

            // load dashboard
            try {
                if (areFieldsFilled && services.isUsernameValid(user) && services.isPasswordValid(user)) {
                    loginUsernameField.clear();
                    loginPasswordField.clear();
                    sceneManager.setLoginController(this);

                    Stage stage = (Stage) goToSignUpPage.getScene().getWindow();

                    // if user is admin load admin dashboard
                    if (services.isUserAdmin(user)) {
                        sceneManager.switchScenes(stage, "Admin.fxml");
                    } else {
                        // else load user dashboard
                        sceneManager.switchScenes(stage, "User.fxml");
                        Session.setUserLoggedIn(username);
                    }
                }
            } catch (SQLException | IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }
}
