package org.example.taskflow.dto;

public class LoginResponse {

    private String message;
    private Long userId;
    private String username;
    private boolean isSuccess;

    public LoginResponse(String message, boolean isSuccess){
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public LoginResponse(Long userId, String username, String message, boolean isSuccess){
        this.message = message;
        this.isSuccess = isSuccess;
        this.userId = userId;
        this.username = username;
    }

    public LoginResponse(){}

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setSuccess(boolean isSuccess) { this.isSuccess = isSuccess; }

    public boolean isSuccess(){ return isSuccess; }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
}
