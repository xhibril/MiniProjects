package org.example.taskflow.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.LoginResponse;
import org.example.taskflow.model.User;
import org.example.taskflow.services.UserService;
import org.example.taskflow.utils.CommonUtils;
import org.example.taskflow.utils.Notifier;
import org.example.taskflow.utils.Redirect;
import org.example.taskflow.utils.Session;

public class AuthController {

    @FXML private Text pageTitle;
    @FXML private Hyperlink pageHandler;
    @FXML private Label notifLabel;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button submitBtn;


    enum AuthMode {
        SIGNUP,
        LOGIN
    }
    AuthMode authMode = AuthMode.LOGIN;

    private final UserService userService = new UserService();
    private final Redirect redirecter = new Redirect();

    @FXML
    public void initialize(){
        setPageHandler();
        setSubmitBtn();
        usernameField.setVisible(false);
    }

    public void setPageHandler() {
        pageHandler.setOnAction(e -> {
            switch (authMode) {
                case SIGNUP -> {
                    authMode = AuthMode.LOGIN;
                    clearFields();
                    loginLayout();
                }
                case LOGIN -> {
                    authMode = AuthMode.SIGNUP;
                    clearFields();
                    signUpLayout();
                }
            }
        });
    }


    public void setSubmitBtn() {
        submitBtn.setOnAction(e -> {

            switch (authMode) {
                case SIGNUP -> {
                    if (CommonUtils.areEmpty(usernameField, emailField, passwordField)) {
                        Notifier.show(notifLabel, "Fill all fields", "ERROR");
                        return;
                    }

                    ApiResponse res = userService.registerUser(
                            new User(
                            usernameField.getText(),
                            emailField.getText(),
                            passwordField.getText())
                    );

                    if (res.isSuccess()) {
                        Notifier.showSuccess(notifLabel, res.getMessage());
                        clearFields();
                    } else {
                        Notifier.showError(notifLabel, res.getMessage());
                    }
                }


                case LOGIN -> {
                    if (CommonUtils.areEmpty(emailField, passwordField)){
                        Notifier.show(notifLabel, "Fill all fields", "ERROR");
                        return;
                    }

                    LoginResponse res = userService.login(new User(emailField.getText(), passwordField.getText()));

                    if(res.isSuccess()){
                        Session.setUser(res.getUserId(), res.getUsername());
                        redirecter.redirect(e, "Dashboard.fxml");
                    } else {
                        Notifier.showError(notifLabel, res.getMessage());

                    }
                }
            }
        });
    }

    public void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }


    public void loginLayout(){
        passwordField.setLayoutY(200);
        emailField.setLayoutY(146);
        usernameField.setVisible(false);
        pageTitle.setText("Sign up");
        pageHandler.setText("Already have an account? Login");
    }

    public void signUpLayout(){
        passwordField.setLayoutY(221);
        emailField.setLayoutY(167);
        usernameField.setVisible(true);
        pageTitle.setText("Sign up");
        pageHandler.setText("Already have an account? Login");
    }
}
