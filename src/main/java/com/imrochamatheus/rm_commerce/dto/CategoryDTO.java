package com.imrochamatheus.rm_commerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;
}
