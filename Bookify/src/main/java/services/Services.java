package services;
import dao.CategoryDAO;
import dao.SlotDao;
import dao.UserDao;
import model.Session;
import model.Slot;
import model.User;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class Services {

   private CategoryDAO categoryDao = new CategoryDAO();
   private SlotDao slotDao = new SlotDao();
   private UserDao userDao = new UserDao();

   // add a category
    public void addNewCategory(String userInput) throws SQLException{
         categoryDao.addNewCategory(userInput);
    }

    // add a slot to a category
    public void addNewSlot(String selectedCategory, String userInput) throws SQLException{

        // get id of category user chose (so it can be used as foreign key for slots)
         int categoryID = categoryDao.getCategoryID(selectedCategory);

        // create the model
         Slot slot = new Slot(userInput, categoryID, Session.getUsernameLoggedIn());

        // add the slot
        slotDao.addSlot(slot);
    }


    // remove a category
    public void removeCategory(String userInput) throws SQLException{
        categoryDao.deleteCategory(userInput);
    }


    // remove a slot from a category
    public void removeSlot(String selectedCategory, String userInput) throws SQLException{
        int categoryID = categoryDao.getCategoryID(selectedCategory);

        Slot slot = new Slot(userInput, categoryID, Session.getUsernameLoggedIn());
        slotDao.deleteSlot(slot);
    }


    // create new user
    public void createNewUser(String username, String password) throws SQLException {
        User newUser = new User(username, password, "USER");
        userDao.insertNewUser(newUser);
    }

    // check if user has entered both username and password, so they may press enter and continue
    public boolean areFieldsFilled(String username, String password){
        boolean areFieldsFilled = false;

        if (password != null && !(password.isEmpty()) && password.matches("[a-zA-z\\d]+")
                && username != null && !(username.isEmpty()) && username.matches("[a-zA-z\\d]+")) {
            areFieldsFilled = true;
        }
        return areFieldsFilled;
    }

    // get id of a category
    public int getCategoryID(String selectedCategory) throws SQLException {
        return categoryDao.getCategoryID(selectedCategory);
    }

    // get stored categories
    public ArrayList<String> getStoredCategories() throws SQLException {
        return categoryDao.getStoredCategories();
    }

    // get stored slots in a category
    public ArrayList<SlotRecord> getStoredSlots(int categoryID) throws SQLException {
        return slotDao.getStoredSlots(categoryID);
    }






    // book a slot
    public void bookSlot(Slot slot) throws SQLException {
        slotDao.bookSlot(slot);
    }

    // unbookm a slot
    public void unBookSlot(Slot slot) throws SQLException{
        slotDao.unBookSlot(slot);
    }

    // check slot availability
    public Boolean checkSlotAvailability(Slot slot) throws SQLException {
        return slotDao.checkSlotAvailability(slot);
    }

    // check who has booked a slot
    public String whoHasSlotBooked(Slot slot) throws SQLException{
        return slotDao.whoHasBookedSlot(slot);
    }


    // check if username exists in DB
    public Boolean isUsernameValid(User user) throws SQLException {
        return userDao.isUsernameValid(user);
    }

    // check if the password corresponding to that username is correct
    public Boolean isPasswordValid(User user) throws SQLException{
        return userDao.isPasswordValid(user);
    }

    // check if user is an admin
    public Boolean isUserAdmin(User user) throws SQLException{
        return userDao.isUserAdmin(user);
    }

    public void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }



}
