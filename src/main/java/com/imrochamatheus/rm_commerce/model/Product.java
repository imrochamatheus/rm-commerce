package com.imrochamatheus.rm_commerce.model;

import com.imrochamatheus.rm_commerce.dto.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String name;
    private Double price;
    private String imgUrl;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ManyToMany()
    @JoinTable(
            name = "tb_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategories (Set<Category> categories){
        this.categories.addAll(categories);
    }
}
