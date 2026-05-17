package org.example.trainsys.services;
import org.example.trainsys.dao.UserDAO;
import org.example.trainsys.dto.LoginResponse;
import org.example.trainsys.dto.SignUpResponse;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public SignUpResponse registerUser(String email, String password, String type){
        return userDAO.registerUser(email, password, type);
    }


    public LoginResponse login(String email, String password, String type){
        return userDAO.login(email, password, type);
    }
}
