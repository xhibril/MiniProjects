package Helpers;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Writer {

    // write user input amount for earnings / spendings
    void writeToFile(int amountSpent, String fileName) {

        LocalDate today = LocalDate.now();
        String dateDetails = today.toString();

        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(dateDetails + "-" + amountSpent + "\n");

        } catch (IOException e) {
            NotificationHandler.getInstance().callNotificationHandler("Could not save data, try again.");
        }
    }
}

















