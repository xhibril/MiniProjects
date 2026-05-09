package org.example.taskflow.utils;

import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;

public class CommonUtils {
    public static void setUpSideBar(Pane homeBtn, Pane taskBtn, Button logoutBtn){
        Redirect redirecter = new Redirect();

        homeBtn.setOnMouseClicked(e->{
            redirecter.redirect(e, "Dashboard.fxml");
        });

        taskBtn.setOnMouseClicked(e->{
            redirecter.redirect(e, "TaskBoard.fxml");
        });


        logoutBtn.setOnAction(e -> {
            redirecter.redirect(e, "AuthPanel.fxml");
        });
    }

    public static boolean areEmpty(TextInputControl... fields) {
        for (TextInputControl field : fields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
