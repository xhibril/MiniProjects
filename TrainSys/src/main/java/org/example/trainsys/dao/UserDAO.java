package org.example.trainsys.dao;
import org.example.trainsys.dto.LoginResponse;
import org.example.trainsys.dto.SignUpResponse;
import org.example.trainsys.utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public SignUpResponse registerUser(String email, String password, String role) {
        try {
            // check if email exists
            String checkSql = "SELECT 1 FROM users WHERE email = ?";
            PreparedStatement checkStmt = DBConnection.getConn().prepareStatement(checkSql);
            checkStmt.setString(1, email);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return new SignUpResponse("Account already exists", false);
            }

            String insertSql = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = DBConnection.getConn().prepareStatement(insertSql);

            insertStmt.setString(1, email);
            insertStmt.setString(2, password);
            insertStmt.setString(3, role.equals("USER") ? "USER" : "ADMIN");
            insertStmt.executeUpdate();

            return new SignUpResponse("Successfully registered", true);

        } catch (SQLException e) {
            return new SignUpResponse("Something went wrong, please try again", false);
        }
    }


    public LoginResponse login(String email, String password, String role) {
        String sql = "SELECT * FROM users WHERE email = ? AND role = ?";

        try (PreparedStatement stmt = DBConnection.getConn().prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, role);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String emailStored = rs.getString("email");
                String passwordStored = rs.getString("password");

                if (!(email.equals(emailStored) && password.equals(passwordStored))) {
                    return new LoginResponse("Invalid credentials", false);
                }

                Long id = rs.getLong("id");
                String accRole = rs.getString("role");
                return new LoginResponse(id, accRole, true);
            }


        } catch (SQLException ex) {
            return new LoginResponse("Something went wrong please try again", false);
        }
        return new LoginResponse("Invalid credentials", false);
    }
}
