package org.example.taskflow.services;

import org.example.taskflow.dao.UserDAO;
import org.example.taskflow.dto.ApiResponse;
import org.example.taskflow.dto.LoginResponse;
import org.example.taskflow.model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public ApiResponse registerUser(User user){
        return userDAO.registerUser(user);
    }

    public LoginResponse login(User user){
        return userDAO.login(user);
    }
}
