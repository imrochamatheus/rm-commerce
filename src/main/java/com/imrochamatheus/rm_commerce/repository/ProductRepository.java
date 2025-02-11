package com.imrochamatheus.rm_commerce.repository;

import com.imrochamatheus.rm_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<Product> findByNameIgnoreCase(String name);
    public List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p JOIN FETCH p.categories WHERE p IN :products")
    public List<Product> findProductsCategories(List<Product> products);


}
