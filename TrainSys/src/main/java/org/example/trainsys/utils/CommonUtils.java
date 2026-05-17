package org.example.trainsys.utils;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;

public class CommonUtils {

    public static void setUpLogout(Button logoutBtn){

        logoutBtn.setOnAction(event -> {
            Redirect redirect = new Redirect();
            redirect.redirect(event, "AuthPanel.fxml");
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

    public static boolean isComboEmpty(ComboBox<?>... boxes) {
        for (ComboBox<?> box : boxes) {
            if (box.getValue() == null) {
                return true;
            }
        }
        return false;
    }
}
