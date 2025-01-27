package com.imrochamatheus.rm_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDTO {
    private Integer status;
    private String error;
    private String path;
    private Instant timestamp;
}
