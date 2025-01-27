package com.imrochamatheus.rm_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorDTO extends ApiErrorDTO {
    private Map<String, String> fieldErrors = new HashMap<>();

    public void addError (String key, String value) {
        this.fieldErrors.put(key, value);
    }
}
