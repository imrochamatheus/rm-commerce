package com.imrochamatheus.rm_commerce.dto;

import com.imrochamatheus.rm_commerce.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotNull(message = "id is required", groups = {OnUpdate.class})
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    private String name;
}
