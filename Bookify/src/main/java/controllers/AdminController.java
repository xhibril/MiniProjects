package controllers;
import services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.SlotRecord;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminController {

    @FXML private Button adminLogoutBtn;
    @FXML private TextField adminTextField;
    @FXML private Button createBtn;
    @FXML private Button removeBtn;
    @FXML private VBox categoriesAndSlotsContainer;
    @FXML private Button adminHomeButton;


    private enum Type{
        CATEGORY,
        SLOT
    } Type type = Type.CATEGORY;


    // ref to helpers / managers
    private final SceneManager sceneManager = new SceneManager();
    private Services services;

    ArrayList<String> storedCategories = new ArrayList<>();
    ArrayList<SlotRecord> storedSlots = new ArrayList<>();

    String selectedCategory = null;
    int categoryID;

    @FXML public void initialize() throws SQLException {
        services = new Services();

        displayCategories();
        setCreateBtn(type);
        setRemoveBtn(type);
        setAdminHomeButton();
        setAdminLogoutBtn();
       // new Alert(Alert.AlertType.ERROR, "Invalid credentials!").showAndWait();

    }

    public void displayCategories() throws SQLException {
        // get the stored categories
        storedCategories = services.getStoredCategories();

        // display them and wait for if user clicks any of them
        for(String s :  storedCategories) {
            Button button = new Button(s);

            button.setOnAction(e -> {
                // get category name that was clicked
                selectedCategory = button.getText();
                type = Type.SLOT;

                try {
                    setCreateBtn(type);
                    setRemoveBtn(type);
                } catch (SQLException ex) {
                    services.showError("Something went wrong, please try again later");
                }

                // display the slots available for that category
                try {
                    displaySlots();
                } catch (SQLException ex) {
                    services.showError("Something went wrong, please try again later");
                }
            });
            categoriesAndSlotsContainer.getChildren().add(button);
        }
    }

    public void displaySlots() throws SQLException{
        // get the category id (used for foreign key in slots)
         categoryID =  services.getCategoryID(selectedCategory);

        // display all the slots found in that category
        storedSlots = services.getStoredSlots(categoryID);

        categoriesAndSlotsContainer.getChildren().clear();
        for (int i = 0; i < storedSlots.size(); i++){

            SlotRecord record = storedSlots.get(i);
            String slot = record.slot();
            Boolean isBooked = record.isBooked();

            Button button = new Button(slot);

            if(isBooked){
                button.setStyle("-fx-background-color: #db6161");
            } else {
                button.setStyle("-fx-background-color: #a8e49f");
            }

            categoriesAndSlotsContainer.getChildren().add(button);
        }
    }


    private void setCreateBtn(Type type){
        createBtn.setOnAction(e->{

            String userInput = adminTextField.getText();
            if(userInput.isEmpty()){
                services.showError("Please enter a name first!");
                return;
            }

            // check if we are creating a category or slot
            try {
                switch (type){
                case CATEGORY -> {
                    services.addNewCategory(userInput);
                    refreshCategories();
                }
                case SLOT -> {
                    services.addNewSlot(selectedCategory, userInput);
                    refreshSlots();
                 }
                }
            } catch (SQLException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }


    private void setRemoveBtn(Type type) throws SQLException{
        removeBtn.setOnAction(e ->{
            // get user input
                 String userInput = adminTextField.getText();
                 if(userInput.isEmpty()){
                     services.showError("Please enter a name first!");
                     return;
                 }
                 adminTextField.clear();


            //check if we are removing a category or slot
            try {
                switch(type){
                    case CATEGORY -> {
                        services.removeCategory(userInput);
                        refreshCategories();
                    }
                    case SLOT -> {
                        services.removeSlot(selectedCategory,userInput);
                        refreshSlots();
                    }
                }
            } catch (SQLException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }


    // set home button
    private void setAdminHomeButton(){
        adminHomeButton.setOnAction(e->{
            Stage stage = (Stage) adminLogoutBtn.getScene().getWindow();
            try {
                sceneManager.switchScenes(stage, "Admin.fxml");
            } catch (IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }

    // log out button
    private void setAdminLogoutBtn(){
        adminLogoutBtn.setOnAction(e ->{
            Stage stage = (Stage) adminLogoutBtn.getScene().getWindow();
            try {
                sceneManager.switchScenes(stage, "Login.fxml");
            } catch (IOException ex) {
                services.showError("Something went wrong, please try again later");
            }
        });
    }

    private void refreshCategories() throws SQLException {
        categoriesAndSlotsContainer.getChildren().clear();
        displayCategories();
    }

    private void refreshSlots() throws SQLException{
        categoriesAndSlotsContainer.getChildren().clear();
        displaySlots();
    }
}
