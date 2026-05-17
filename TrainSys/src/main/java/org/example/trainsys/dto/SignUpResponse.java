package org.example.trainsys.dto;

public class SignUpResponse {
    private String message;
    private boolean isSuccess;

    public SignUpResponse(String message, boolean isSuccess){
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
