import java.sql.SQLException;

public class Coordinator {

    // ref to other classes
    DatabaseManager databaseManager = new DatabaseManager();

    private InputManager inputManager;
    public Coordinator() throws SQLException {
        inputManager = new InputManager(this);
    }


    public enum FindBy {
        NONE,
        ID,
        NAME,
    } FindBy findBy = FindBy.NONE;

    // "apis" used by input manager
    public void setFindByID() { findBy = FindBy.ID; }
    public void setFindByName() { findBy = FindBy.NAME; }


    // print main menu
    public int MainMenu() {
        System.out.println("""
                 =========================
                 📒 CONTACT BOOK MENU
                 =========================
                 1️⃣  Add New Contact
                 2️⃣  View All Contacts
                 3️⃣  Search Contact by ID or Name
                 4️⃣  Delete Contact by ID or Name
                 5️⃣  Save & Exit
                -------------------------
                Select an option (1–5)
                """);

      return inputManager.mainMenuPick();
    }

    // add a new contact
    public void addNewContact(){
        System.out.println("Add new contact.");

        String nameInput = inputManager.getNameInput();
        String phoneNumberInput = inputManager.getPhoneInput();
        String emailInput = inputManager.getEmailInput();

        try {
            databaseManager.addContactToDataBase(nameInput, phoneNumberInput, emailInput);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        System.out.println("Contact added successfully.");
    }

    // view all contacts
    public void viewAllContacts(){
        try {
            databaseManager.viewAllContacts();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // get user input, search the database and display the contact(s) info if it exists
    public void searchContact(){
        String userInput = null;
         try {
            userInput =   inputManager.getContact();
           } catch (SQLException e) {
           System.out.println("Database error: " + e.getMessage());
           }

        switch (findBy) {
            case ID -> {
               try {
                   databaseManager.searchContact(userInput, findBy.ID);
               } catch (SQLException e) {
                   System.out.println("Database error: " + e.getMessage());
               }
            }
            case NAME -> {
               try {
                   databaseManager.searchContact(userInput, findBy.NAME);
               } catch (SQLException e) {
                   System.out.println("Database error: " + e.getMessage());
               }
            }
        }
    }

    // get user input, search the database and delete the contact(s) if it exists
    public void deleteContact() {
        String userInput = null;
         try {
             userInput = inputManager.getContact();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        switch (findBy) {
            case ID -> {
                try {
                    databaseManager.deleteContact(userInput, findBy.ID);
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }
            case NAME -> {
                try {
                    databaseManager.deleteContact(userInput, findBy.NAME);
                } catch (SQLException e) {
                    System.out.println("Database error: " + e.getMessage());
                }
            }
        }
    }
}
