package dao;
import model.User;
import utils.DBconnection;
import java.sql.*;
public class UserDao {

    // insert new user
    public void insertNewUser(User user) throws SQLException {

        String insertNewUser = "INSERT INTO users(username, password, role)" +
                "VALUES(?, ?, ?)";

        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(insertNewUser)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
        }
    }

// check if username exists in the DB
    public boolean isUsernameValid(User user) throws SQLException {

        String checkIfUserExits = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(checkIfUserExits)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {

                // return true if username is found
                return rs.next();
            }
        }
    }

// check if the password matches
    public boolean isPasswordValid(User user) throws SQLException {
        boolean isPasswordCorrect = false;

        // check password by username
        String checkIfPasswordIsValid = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement ps = DBconnection.getConnection().prepareStatement(checkIfPasswordIsValid)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()){

            // check if password user entered matches password stored
            if (rs.next()) {
                if (rs.getString("password").equals(user.getPassword()))
                    isPasswordCorrect = true;
            }
        }
    }
        return isPasswordCorrect;
    }

// checks if user is admin
    public boolean isUserAdmin(User user) throws SQLException{
        String userRole = null;

        // get role by checking their username
        String checkIfUserIsAdmin = "SELECT role FROM users WHERE username = ?";
        try(PreparedStatement ps = DBconnection.getConnection().prepareStatement(checkIfUserIsAdmin)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    userRole = rs.getString("role");
                }
            }
        }
        // returns true if role is admin
        return userRole.matches("ADMIN");
    }
}
