package com.imrochamatheus.rm_commerce.service;

import com.imrochamatheus.rm_commerce.dto.ProductDTO;
import com.imrochamatheus.rm_commerce.exception.NotFoundException;
import com.imrochamatheus.rm_commerce.exception.ResourceAlreadyExistsException;
import com.imrochamatheus.rm_commerce.exception.ResourceIntegrityViolation;
import com.imrochamatheus.rm_commerce.model.Category;
import com.imrochamatheus.rm_commerce.model.Product;
import com.imrochamatheus.rm_commerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    private ProductDTO toDTO (Product product) {
        return this.modelMapper.map(product, ProductDTO.class);
    }

    private Product fromDTO (ProductDTO productDTO) {
        return this.modelMapper.map(productDTO, Product.class);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllPaginated (Pageable pageable) {
        Page<Product> productPage = this.productRepository.findAll(pageable);
        this.productRepository.findProductsCategories(productPage.toList());

        return productPage.map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO getByID (Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id +  " does not exists"));

        return this.toDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getByName (String name) {
        List<Product> products = this.productRepository.findByNameContainingIgnoreCase(name.toUpperCase());

        if(products.isEmpty()) {
            throw new NotFoundException("Product with name " + name +  " does not exists");
        }

        this.productRepository.findProductsCategories(products);
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Optional<Product> productOptional = this.productRepository.findByNameIgnoreCase(productDTO.getName());

        if(productOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("Product already exists");
        }

        productDTO.setId(null);
        Product product = this.fromDTO(productDTO);

        this.productRepository.save(product);
        return this.toDTO(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id +  " does not exists"));

        BeanUtils.copyProperties(productDTO, product);
        product.setId(id);
        product.getCategories().clear();

        productDTO.getCategories().forEach(categoryDTO -> {
            product.getCategories().add(this.modelMapper.map(categoryDTO, Category.class));
        });

        this.productRepository.save(product);
        return this.toDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id +  " does not exists"));

        if(!product.getCategories().isEmpty()) {
            throw new ResourceIntegrityViolation("Product cannot be deleted as it has associated categories");
        }

        this.productRepository.delete(product);
    }
}
