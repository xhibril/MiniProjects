package dao;
import utils.DBconnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {


    public void addNewCategory(String name) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)" ;
       try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)){
           ps.setString(1, name);
           ps.executeUpdate();
       }
    }


    public ArrayList<String> getStoredCategories() throws SQLException{
        ArrayList<String> categories = new ArrayList<>();

        String sql = "SELECT name FROM categories";
        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)){
           try (ResultSet rs = ps.executeQuery()) {

               while (rs.next()) {
                   categories.add(rs.getString("name"));
               }
           }
        }
        return categories;
    }

    public void deleteCategory(String name) throws SQLException {

        String sql = "DELETE FROM categories WHERE name = ?";
        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    // needed to assign to slots
    public int getCategoryID(String name) throws SQLException {

        String sql = "SELECT id FROM categories WHERE name = ?";
        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        return -1;
    }
}
