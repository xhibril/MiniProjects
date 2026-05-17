package org.example.trainsys.utils;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Notifier {
    private static PauseTransition delay;

    public static void showError(Label notifLabel, String msg) {
        Notifier.show(notifLabel, msg, "ERROR");
    }

    public static void showSuccess(Label notifLabel, String msg) {
        Notifier.show(notifLabel, msg, "SUCCESS");
    }

    public static void show(Label notifLabel, String message, String type){
        switch (type){
            case "ERROR" -> {
                notifLabel.setStyle(
                        "-fx-background-color: #ff4d4f;" +
                                "-fx-text-fill: white;" +
                                "-fx-padding: 10;" +
                                "-fx-background-radius: 8;" +
                                "-fx-font-weight: bold;"
                );
            }

            case "SUCCESS" -> {
                notifLabel.setStyle(
                        "-fx-background-color: #52c41a;" +
                                "-fx-text-fill: white;" +
                                "-fx-padding: 10;" +
                                "-fx-background-radius: 8;" +
                                "-fx-font-weight: bold;"
                );
            }

            default -> {
                notifLabel.setStyle(
                        "-fx-background-color: #333333;" +
                                "-fx-text-fill: white;" +
                                "-fx-padding: 10;" +
                                "-fx-background-radius: 8;"
                );
            }
        }


        notifLabel.setText(message);
        notifLabel.setVisible(true);

        if (delay != null) {
            delay.stop();
        }

        delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> notifLabel.setVisible(false));
        delay.play();
    }
}