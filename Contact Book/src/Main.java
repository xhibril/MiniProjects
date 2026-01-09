import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {


        Coordinator coordinator = new Coordinator();
        int userPick;

        do {
            userPick = coordinator.MainMenu();
            switch (userPick) {
                case 1 -> coordinator.addNewContact();
                case 2 -> coordinator.viewAllContacts();
                case 3 -> coordinator.searchContact();
                case 4 -> coordinator.deleteContact();
                case 5 -> {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
            }
            waitForEnter();
        } while (userPick != 5);

    }


    private static void waitForEnter() {
        System.out.println("Press enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

}