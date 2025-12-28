package dao;

import model.Slot;
import services.SlotRecord;
import utils.DBconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SlotDao {

    public void addSlot(Slot slot) throws SQLException {
        String sql = "INSERT INTO slots (slot, category_id, username) VALUES (?, ?, ?)";

        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, slot.getSlot());
            ps.setInt(2, slot.getCategoryID());
            ps.setString(3, slot.getUsername());
            ps.executeUpdate();
        }
    }


    public ArrayList<SlotRecord> getStoredSlots(int categoryID) throws SQLException {
        ArrayList<SlotRecord> slots = new ArrayList<>();
        String sql = "SELECT slot, is_booked FROM slots WHERE category_id = ?";

        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, categoryID);
            try (ResultSet rs = ps.executeQuery();) {

                while (rs.next()) {
                    slots.add(new SlotRecord(rs.getString("slot"), rs.getBoolean("is_booked")));
                }
            }
        }
        return slots;
    }

    public void deleteSlot(Slot slot) throws SQLException{
        String sql = "DELETE FROM slots WHERE category_id = ? AND slot = ?";

        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, slot.getCategoryID());
            ps.setString(2, slot.getSlot());
            ps.executeUpdate();
        }
    }


    public Boolean checkSlotAvailability(Slot slot) throws SQLException {
        Boolean isBooked = false;
        String sql = "SELECT is_booked FROM slots WHERE category_id = ? AND slot = ?";

        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, slot.getCategoryID());
            ps.setString(2, slot.getSlot());
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) isBooked = rs.getBoolean("is_booked");
            }
        }
        return isBooked;
    }

    public String whoHasBookedSlot(Slot slot) throws SQLException{
        String username = "";
        String sql = "SELECT username FROM slots WHERE category_id = ? AND slot = ?";

        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)){
            ps.setInt(1, slot.getCategoryID());
            ps.setString(2, slot.getSlot());
            try(ResultSet rs = ps.executeQuery()){

                if(rs.next()) username = rs.getString("username");
            }
        }
        return username;
    }

    public void bookSlot(Slot slot) throws SQLException{
        String sql = "UPDATE slots SET is_booked = true, username = ? WHERE slot = ? AND category_id = ?";

        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, slot.getUsername());
            ps.setString(2, slot.getSlot());
            ps.setInt(3, slot.getCategoryID());
            ps.executeUpdate();
        }
    }

    public void unBookSlot(Slot slot) throws SQLException{
        String sql = "UPDATE slots SET is_booked = false, username = NULL WHERE slot = ? AND category_id = ?";

        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, slot.getSlot());
            ps.setInt(2, slot.getCategoryID());
            ps.executeUpdate();
        }
    }


}
