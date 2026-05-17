package org.example.trainsys.dto;

public class ApiResponse {

    private String message;
    private boolean isSuccess;

    public ApiResponse(String message){
        this.message = message;
    }

    public ApiResponse(String message, boolean isSuccess){
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
}
