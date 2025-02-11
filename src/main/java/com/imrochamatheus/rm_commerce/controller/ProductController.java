package com.imrochamatheus.rm_commerce.controller;

import com.imrochamatheus.rm_commerce.dto.ProductDTO;
import com.imrochamatheus.rm_commerce.service.ProductService;
import com.imrochamatheus.rm_commerce.validation.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllPaginated (Pageable pageable) {
        return ResponseEntity.ok(this.productService.getAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.getByID(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(this.productService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = this.productService.saveProduct(productDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(productDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id, @Validated({OnUpdate.class, Default.class}) @RequestBody ProductDTO productDTO) {

        return ResponseEntity.ok(this.productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable Long id) {
        this.productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
