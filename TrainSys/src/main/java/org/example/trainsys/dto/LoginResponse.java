package org.example.trainsys.dto;

public class LoginResponse {
    private Long userId;
    private String role;
    private String message;
    private boolean isSuccess;

    public LoginResponse(Long userId, String role, boolean isSuccess) {
        this.userId = userId;
        this.role = role;
        this.isSuccess = isSuccess;
    }

    public LoginResponse(String message, boolean isSuccess){
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setSuccess(boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess(){
        return isSuccess;
    }

    public Long getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}