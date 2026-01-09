import java.sql.SQLException;
import java.util.Scanner;
public class InputManager {

    Scanner input = new Scanner(System.in);

    private Coordinator coordinator;
    public InputManager(Coordinator coordinator) throws SQLException {
        this.coordinator = coordinator;
    }


    // pick an option from main menu
    public int mainMenuPick() {
        int userPick = -1;

        // check if user input is valid
        do {
            if (input.hasNextInt()) {
                userPick = input.nextInt();
                if (userPick >= 1 && userPick <= 5) {
                    break;
                } else {
                    System.out.println("Invalid input, choose between 1-5.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
            }
            input.nextLine();
        } while (true);
        input.nextLine();
        return userPick;
    }

    // get contact name
    public String getNameInput(){
        String nameInput;

        // check if name isn't too short or too long
        while(true){
            System.out.println("Name: ");
            nameInput = input.nextLine();

            // allow all chars and spaces
            if (nameInput.matches("[a-zA-Z ]+")) {
                if (nameInput.length() >= 2 && nameInput.length() <= 50) break;
                System.out.println("Invalid name, please try again.");
            } else {
                System.out.println("You may only use characters, please try again.");
            }
        }
        return nameInput;
    }

    // get contact email
    public String getEmailInput(){
        String emailInput;

      // check if email isn't too long
      while(true){
        System.out.println("Email: ");
        emailInput = input.nextLine();

        // allow all chars, numbers, spaces, @ and .
       if (emailInput.matches("[a-zA-Z\\d@\\. ]+")) {
           if (emailInput.length() <= 50) break;
           System.out.println("Invalid input, please try again.");
       } else {
           System.out.println("Invalid email, please try again.");
       }
    }
        return emailInput;
    }

    // get contact phone number
    public String getPhoneInput(){
        String phoneNumberInput;

        while(true){
            System.out.println("Phone: ");
            phoneNumberInput = input.nextLine();

            // allow all numbers and + symbol
            if (phoneNumberInput.matches("[\\d +]+")){
                if (phoneNumberInput.length() >= 10 && phoneNumberInput.length() <= 15)  break;
                System.out.println("Invalid input, please try again");
            } else {
                System.out.println("Invalid number, please try again");
            }

        }
        return phoneNumberInput;
    }


    // used in search / delete contact
   public String getContact() throws SQLException {
        System.out.println("Enter ID or Name: ");
        String userInput = input.nextLine();

      try {
          int checkIfItsID = Integer.parseInt(userInput);
          coordinator.setFindByID();
          return userInput;

      } catch (NumberFormatException e) {
          coordinator.setFindByName();
          return userInput;
      }
  }

}
