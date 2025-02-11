package com.imrochamatheus.rm_commerce.dto;

import com.imrochamatheus.rm_commerce.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProductDTO {
    @NotNull(message = "id is required", groups = {OnUpdate.class})
    private Long id;

    @NotBlank(message = "description is required")
    @Min(value = 10, message = "description should have at least 10 characters")
    private String description;

    @NotBlank(message = "name is required")
    @Min(value = 3, message = "name should have at least 3 characters")
    @Max(value = 80, message = "name should have a maximum of 80 characters")
    private String name;

    @Positive(message = "price should be positive")
    private Double price;

    @NotBlank(message = "imgUrl is required")
    private String imgUrl;

    @NotEmpty(message = "must have at least one category")
    @Valid
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    private Set<CategoryDTO> categories = new HashSet<>();

    public void addCategories (Set<CategoryDTO> categoryDTOS){
        this.categories.addAll(categoryDTOS);
    }
}
