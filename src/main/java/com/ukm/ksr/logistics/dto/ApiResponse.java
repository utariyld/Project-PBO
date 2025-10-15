package com.ukm.ksr.logistics.dto;

public class ApiResponse {
    private String message;
    private Object data;
    private boolean success;

    // Constructors
    public ApiResponse() {}

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.success = data != null;
    }

    public ApiResponse(String message, Object data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}