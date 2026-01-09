package controllers;
import model.Session;
import model.Slot;
import services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.SlotRecord;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {

    @FXML private Button userLogoutBtn;
    @FXML private VBox categoriesContainer;
    @FXML private Button userHomeButton;
    @FXML private Label pageSpecifier;


    private final SceneManager sceneManager = new SceneManager();
    private Services services;
    private Slot slot;

    ArrayList<String> storedCategories = new ArrayList<>();
    ArrayList<SlotRecord> storedSlots = new ArrayList<>();
    String selectedCategory = null;


    @FXML public void initialize() throws SQLException {
        services = new Services();
        displayCategoriesAndListenForInput();
        setUserHomeButton();
        setUserLogoutBtn();
    }

    // displays categories and checks for input
    private void displayCategoriesAndListenForInput() throws SQLException {
        // read stored categories
        storedCategories = services.getStoredCategories();

        for(String s :  storedCategories) {
            Button button = new Button(s);

            button.setOnAction(e -> {
                selectedCategory = button.getText();
                try {
                    displaySlots();
                    // change page title to slots
                    pageSpecifier.setText("Slots");
                } catch (SQLException ex) {
                    services.showError("Something went wrong, please try again later");
                }
            });
            categoriesContainer.getChildren().add(button);
        }
    }





    private void displaySlots() throws SQLException{
        int categoryID =  services.getCategoryID(selectedCategory);

        // read all the stored slots in the selected category
        storedSlots = services.getStoredSlots(categoryID);

        // display them
        categoriesContainer.getChildren().clear();
        for (int i = 0 ; i < storedSlots.size(); i++){
            SlotRecord record = storedSlots.get(i);

            String slotName = record.slot();
            Boolean isBooked = record.isBooked();
            Button button = new Button(slotName);

            if(isBooked){
                button.setStyle("-fx-background-color: #db6161");
            } else {
                button.setStyle("-fx-background-color: #a8e49f");
            }

            button.setOnAction(e->{
                String selectedSlot = button.getText();
                // check if slot is available
                try {
                    slot = new Slot(selectedSlot, categoryID, Session.getUsernameLoggedIn());

                    // if slot is not booked, book it
                    if(!(services.checkSlotAvailability(slot))){
                        services.bookSlot(slot);
                    } else{

                     // if slot is booked, check if the user who clicked it was the one that booked it
                     // if so unbook it

                      String username = services.whoHasSlotBooked(slot);
                      if(username.equals(Session.getUsernameLoggedIn())){
                          services.unBookSlot(slot);
                      }
                    }
                    // refresh
                    refreshSlots();

                } catch (SQLException ex) {
                    services.showError("Something went wrong, please try again later");
                }
            });
            categoriesContainer.getChildren().add(button);
        }
    }

    // set home button
    private void setUserHomeButton(){
        userHomeButton.setOnAction(e->{
            Stage stage = (Stage) userLogoutBtn.getScene().getWindow();
            try {
                sceneManager.switchScenes(stage, "User.fxml");
                // change page title to categories
                pageSpecifier.setText("Categories");
            } catch (IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }

    // log out button
    private void setUserLogoutBtn(){
        userLogoutBtn.setOnAction(e->{
            Stage stage = (Stage) userLogoutBtn.getScene().getWindow();
            try {
                sceneManager.switchScenes(stage, "Login.fxml");
            } catch (IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }

    private void refreshSlots() throws SQLException {
        categoriesContainer.getChildren().clear();
        displaySlots();
    }
}
