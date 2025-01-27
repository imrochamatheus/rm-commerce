package com.imrochamatheus.rm_commerce.controller;

import com.imrochamatheus.rm_commerce.dto.CategoryDTO;
import com.imrochamatheus.rm_commerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllPaginated (Pageable pageable) {
        return ResponseEntity.ok(this.categoryService.getAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<CategoryDTO> getByName(@RequestParam String name) {
        return ResponseEntity.ok(this.categoryService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryDTO = this.categoryService.saveCategory(categoryDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateById(
            @PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(this.categoryService.updateById(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        this.categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
