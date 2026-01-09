package Helpers;
import Controllers.MainController;
import javafx.scene.paint.Color;

    public class NotificationHandler {

        // single global instance
        private static final NotificationHandler INSTANCE = new NotificationHandler();

        private MainController mainController;
        public void setMainController(MainController main) {
            this.mainController = main;
        }

        // private constructor
        private NotificationHandler() {
        }
        // access point for everyone
        public static NotificationHandler getInstance() {
            return INSTANCE;
        }

        public void callNotificationHandler(String message) {
            if (mainController == null) {
                System.err.println("NotificationHandler - MainController not set!");
                return;
            }
            mainController.accessTopLabel().setText(message);
            mainController.accessTopLabel().setTextFill(Color.web("#FF0000"));
            mainController.showTopLabelsContainer();
        }
    }
