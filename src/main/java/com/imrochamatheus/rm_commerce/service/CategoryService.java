package com.imrochamatheus.rm_commerce.service;

import com.imrochamatheus.rm_commerce.dto.CategoryDTO;
import com.imrochamatheus.rm_commerce.exception.NotFoundException;
import com.imrochamatheus.rm_commerce.exception.ResourceAlreadyExistsException;
import com.imrochamatheus.rm_commerce.exception.ResourceIntegrityViolation;
import com.imrochamatheus.rm_commerce.model.Category;
import com.imrochamatheus.rm_commerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    private CategoryDTO toDTO (Category category) {
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    private Category fromDTO (CategoryDTO categoryDTO) {
      return this.modelMapper.map(categoryDTO, Category.class);
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

    @Transactional(readOnly = true)
    public CategoryDTO getByName(String name) {
        Category category = this.categoryRepository
                .findByNameContainingIgnoreCase(name.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Category with name " + name + " does not exists"));

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
        this.categoryRepository.save(category);

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
