package controllers;
import services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Session;
import java.io.IOException;
import java.sql.SQLException;

public class SignupController {

    @FXML private AnchorPane signupRoot;
    @FXML private TextField signUpUsernameField;
    @FXML private TextField signUpPasswordField;
    @FXML private Hyperlink goToLoginPage;

    String username, password;

    // ref to scene manager and user dao
    private SceneManager sceneManager;
    private Services services;


  @FXML public void initialize(){
      setGoToLoginPage();
      setSignUpPasswordField();
      services = new Services();
      sceneManager = new SceneManager();
  }


// go to login page
    private void setGoToLoginPage(){
        goToLoginPage.setOnAction(e -> {
            sceneManager.setSignupController(this);
            // get current stage
            Stage stage = (Stage) goToLoginPage.getScene().getWindow();

            try {
                sceneManager.switchScenes(stage, "Login.fxml");
            } catch (IOException ex) {
                System.err.println("Could not switch scenes! (Signup -> Login)");
            }
        });
    }


    private void setSignUpPasswordField(){
      signUpPasswordField.setOnAction(e -> {
          // get input
           username = signUpUsernameField.getText();
           password = signUpPasswordField.getText();
           signUpPasswordField.clear();
           signUpUsernameField.clear();

           // check if both fields are filled
         boolean areFieldsFilled = services.areFieldsFilled(username, password);
          if (areFieldsFilled) {
              sceneManager.setSignupController(this);
              Stage stage = (Stage) goToLoginPage.getScene().getWindow();

              // create the new user
              try {
                  services.createNewUser(username, password);
              } catch (SQLException ex) {
                  throw new RuntimeException(ex);
              }

              // load dashboard
              try {
                  sceneManager.switchScenes(stage, "User.fxml");
                  Session.setUserLoggedIn(username);
              } catch (IOException ex) {
                  System.out.println("Something went wrong, please try again later");
              }
          }
      });
    }
}
