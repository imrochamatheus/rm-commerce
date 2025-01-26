package com.imrochamatheus.rm_commerce.service;

import com.imrochamatheus.rm_commerce.dto.CategoryDTO;
import com.imrochamatheus.rm_commerce.exception.NotFoundException;
import com.imrochamatheus.rm_commerce.exception.ResourceAlreadyExistsException;
import com.imrochamatheus.rm_commerce.exception.ResourceIntegrityViolation;
import com.imrochamatheus.rm_commerce.model.Category;
import com.imrochamatheus.rm_commerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryDTO toDTO (Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }

    private Category fromDTO (CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        return category;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getAllPaginated (Pageable pageable) {
        return this.categoryRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getById(Long id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " does not exists"));

        return this.toDTO(category);
    }

    @Transactional
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = this.categoryRepository.findByName(categoryDTO.getName());

        if(optionalCategory.isPresent()) {
            throw new ResourceAlreadyExistsException("Category already exists");
        }

        Category category = this.fromDTO(categoryDTO);
        return this.toDTO(this.categoryRepository.save(category));
    }

    @Transactional
    public CategoryDTO updateById (Long id, CategoryDTO categoryDTO) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id " + id + " does not exists"));

        category.setName(categoryDTO.getName());

        return this.toDTO(category);
    }

    @Transactional
    public void deleteById(Long id) {
        if(!this.categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id " + id + " does not exists");
        }

        try {
            this.categoryRepository.deleteById(id);
            this.categoryRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceIntegrityViolation("Resource cannot be deleted as it is referenced by other entities.");
        }
    }
}
