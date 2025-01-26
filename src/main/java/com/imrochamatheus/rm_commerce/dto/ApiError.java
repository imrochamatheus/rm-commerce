package com.imrochamatheus.rm_commerce.dto;

import java.time.Instant;

public class ApiError {
    private Integer status;
    private String error;
    private String path;
    private Instant timestamp;

    public ApiError() {
    }

    public ApiError(Integer status, String error, String path, Instant timestamp) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
