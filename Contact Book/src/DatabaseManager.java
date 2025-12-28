import java.sql.*;


public class DatabaseManager {

    // connect to database
    Connection conn;

    public DatabaseManager() throws SQLException {
        String url  = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");
        conn = DriverManager.getConnection(url, user, pass);
    }


    // enter new contact info
    public void addContactToDataBase(String name, String phoneNumber, String email) throws SQLException {

        String addNewContact = "INSERT INTO CONTACTS (name, phone, email) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(addNewContact);

        ps.setString(1, name);
        ps.setString(2, phoneNumber);
        ps.setString(3, email);
        ps.executeUpdate();
        ps.close();
    }

    // view all saved contacts
    public void viewAllContacts() throws SQLException{
        String viewAllContacts = "SELECT * FROM contacts";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(viewAllContacts);

        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            String email = rs.getString("email");

            System.out.println("ID - "+ id + " Name: " +name + " Phone: " + phone + " Email: " + email);
        }
        stmt.close();
        rs.close();
    }


    // search a contact by either ID or name
    public void searchContact(String userInput, Coordinator.FindBy findBy)
            throws SQLException {

        String name, phoneNumber, email, searchContact;

        name = phoneNumber = email = null;
        PreparedStatement preStatement = null;

        switch(findBy){
            case ID -> {

                searchContact = "SELECT * FROM contacts WHERE ID = ?";
                preStatement = conn.prepareStatement(searchContact);

                int ID = Integer.parseInt(userInput);
                // ️Fill in the ? values
                preStatement.setInt(1, ID);
            }

            case NAME -> {
                searchContact = "SELECT * FROM contacts WHERE NAME = ?";
                preStatement = conn.prepareStatement(searchContact);
                preStatement.setString(1, userInput);
            }
        }

        ResultSet results = preStatement.executeQuery();
        if (results.next()){
            name = results.getString("name");
            phoneNumber =  results.getString("phone");
            email = results.getString("email");
        }

        System.out.println("Name: " + (name != null ? name : "[Not available]"));
        System.out.println("Phone Number: " + (phoneNumber != null ? phoneNumber : "[Not available]"));
        System.out.println("Email: " + (email != null ? email : "[Not available]"));

        preStatement.close();
        results.close();
    }


    // delete a contact by ID or name
    public void deleteContact(String userInput, Coordinator.FindBy findBy)
            throws SQLException {

        String searchContact;
        PreparedStatement preStatement = null;

        switch(findBy){
            case ID -> {

                searchContact = "DELETE FROM contacts WHERE ID = ?";
                preStatement = conn.prepareStatement(searchContact);

                int ID = Integer.parseInt(userInput);
                // ️Fill in the ? values
                preStatement.setInt(1, ID);
            }

            case NAME -> {
                searchContact = "DELETE FROM contacts WHERE NAME = ?";
                preStatement = conn.prepareStatement(searchContact);
                preStatement.setString(1, userInput);
            }
        }

        int rowsAffected= preStatement.executeUpdate();
        if (rowsAffected > 0){
            System.out.println("Contact(s) deleted successfully.");
        } else {
            System.out.println("No contact found.");
        }
        preStatement.close();
    }
}
