package org.example.taskflow.dao;

import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.LoginResponse;
import org.example.taskflow.model.User;
import org.example.taskflow.utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public ApiResponse registerUser(User user){
        // check if user exists

        String doesUserExist = "SELECT 1 FROM users WHERE username = ? LIMIT 1;";
        String doesEmailExist = "SELECT 1 FROM users WHERE email = ? LIMIT 1;";
        String registerQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement userPs = DBConnection.getConn().prepareStatement(doesUserExist);
            userPs.setString(1, user.getUsername());
            ResultSet userRs = userPs.executeQuery();

            if(userRs.next()){
                return new ApiResponse("Username is already registered", false);
            }


            PreparedStatement emailPs = DBConnection.getConn().prepareStatement(doesEmailExist);
            emailPs.setString(1, user.getEmail());
            ResultSet emailRs = emailPs.executeQuery();

            if(emailRs.next()){
                return new ApiResponse("Email is already registered", false);
            }


            PreparedStatement registerPs = DBConnection.getConn().prepareStatement(registerQuery);
            registerPs.setString(1, user.getUsername());
            registerPs.setString(2, user.getEmail());
            registerPs.setString(3, user.getPassword());
            registerPs.executeUpdate();


            return new ApiResponse("Successfully registered", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public LoginResponse login(User user) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement ps = DBConnection.getConn().prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String emailStored = rs.getString("email");
                String passwordStored = rs.getString("password");

                if (!(user.getEmail().equals(emailStored) && user.getPassword().equals(passwordStored))) {
                    return new LoginResponse("Invalid credentials", false);
                }

                Long id = rs.getLong("id");
                String username = rs.getString("username");
                return new LoginResponse(id, username, "Login successful", true);
            }

        } catch (SQLException ex) {
            return new LoginResponse("Something went wrong please try again", false);
        }
        return new LoginResponse("Invalid credentials", false);
    }
}
