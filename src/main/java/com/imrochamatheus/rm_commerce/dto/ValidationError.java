package com.imrochamatheus.rm_commerce.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ValidationError extends ApiError{
    private Map<String, String> fieldErrors = new HashMap<>();

    public ValidationError() {
    }

    public ValidationError(Integer status, String error, String path, Instant timestamp) {
        super(status, error, path, timestamp);
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void addError (String key, String value) {
        this.fieldErrors.put(key, value);
    }
}
