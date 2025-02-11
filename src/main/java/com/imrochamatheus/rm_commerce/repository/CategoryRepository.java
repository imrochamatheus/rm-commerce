package com.imrochamatheus.rm_commerce.repository;

import com.imrochamatheus.rm_commerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Optional<Category> findByNameIgnoreCase(String name);
    public List<Category> findByNameContainingIgnoreCase(String name);
}
