package org.example.trainsys.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.trainsys.dto.SignUpResponse;
import org.example.trainsys.services.UserService;
import org.example.trainsys.utils.Notifier;
import org.example.trainsys.utils.Redirect;
import org.example.trainsys.utils.Session;
import org.example.trainsys.dto.LoginResponse;
import org.example.trainsys.utils.CommonUtils;

public class AuthController {
    @FXML private Hyperlink handleAuthMode;
    @FXML private Label pageTitle;
    @FXML private TextField password;
    @FXML private TextField email;
    @FXML private Button continueBtn;
    @FXML private Label notifLabel;
    @FXML private RadioButton adminToggleBtn;
    @FXML private RadioButton userToggleBtn;

    enum AuthMode {
        SIGNUP,
        LOGIN
    }
    AuthMode authMode = AuthMode.LOGIN;

    enum AuthType {
        ADMIN,
        USER,
        NONE
    }
    AuthType authType = AuthType.NONE;

    private final UserService userService = new UserService();
    private final Redirect redirecter = new Redirect();

    @FXML
    public void initialize() {
        setupActions();
    }

    public void setPageTitle() {
        switch (authMode) {
            case SIGNUP -> pageTitle.setText("SIGN UP");
            case LOGIN -> pageTitle.setText("LOGIN");
        }
    }

    public void setHandleAuthMode() {
        handleAuthMode.setOnAction(e -> {
            switch (authMode) {
                case LOGIN -> {
                    clearFields();
                    authMode = AuthMode.SIGNUP;
                    handleAuthMode.setText("Already have an account? Login");
                }
                case SIGNUP -> {
                    clearFields();
                    authMode = AuthMode.LOGIN;
                    handleAuthMode.setText("Don't have an account? Signup");
                }
            }
            setPageTitle();
        });
    }

    public void setContinueBtn() {
        continueBtn.setOnAction(event -> {
            if (CommonUtils.areEmpty(email, password)) {
                Notifier.show(notifLabel, "Fill all fields", "ERROR");
                return;
            }

            if(authType == AuthType.NONE){
                Notifier.showError(notifLabel, "Please select an account type");
                return;
            }

            switch (authMode) {
                case SIGNUP -> {
                    SignUpResponse res = userService.registerUser(email.getText().toLowerCase().trim(),
                            password.getText().toLowerCase().trim(), authType == AuthType.USER ? "USER" : "ADMIN");

                    if (res.isSuccess()) {
                        Notifier.showSuccess(notifLabel, res.getMessage());
                    } else {
                        Notifier.showError(notifLabel, res.getMessage());
                    }
                    clearFields();
                }

                case LOGIN -> {
                    LoginResponse res = userService.login(email.getText().toLowerCase().trim(),
                            password.getText().toLowerCase().trim(), authType == AuthType.USER ? "USER" : "ADMIN");

                    if (res.isSuccess()) {
                        redirect(event, res.getUserId(), res.getRole());
                    } else {
                        Notifier.showError(notifLabel, res.getMessage());
                    }
                }
            }
        });
    }

    public void setAuthTypeBtns(){
        ToggleGroup group = new ToggleGroup();
        adminToggleBtn.setToggleGroup(group);
        userToggleBtn.setToggleGroup(group);

        adminToggleBtn.setOnAction(e-> {
            authType = AuthType.ADMIN;
        });

        userToggleBtn.setOnAction(e-> {
            authType = AuthType.USER;
        });
    }

    public void redirect(ActionEvent event, Long id, String role) {
        Session.setUser(id, role);

        switch (role) {
            case "USER" -> redirecter.redirect(event, "UserPanel.fxml");
            case "ADMIN" -> redirecter.redirect(event, "AdminPanel.fxml");
        }
    }



    public void clearFields() {
        email.setText("");
        password.setText("");
    }

    public void setupActions() {
        notifLabel.setVisible(false);
        setHandleAuthMode();
        setPageTitle();
        setContinueBtn();
        setAuthTypeBtns();
    }
}