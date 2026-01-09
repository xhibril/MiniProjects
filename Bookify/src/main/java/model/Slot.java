package model;

public class Slot {

    String slot, username;
    int categoryID;


    public Slot(String slot, int categoryID, String username){
        this.slot = slot;
        this.categoryID = categoryID;
        this.username = username;
    }

    // getters
    public String getSlot(){ return slot; }
    public int getCategoryID() { return categoryID; }
    public String getUsername(){ return username; }

}
