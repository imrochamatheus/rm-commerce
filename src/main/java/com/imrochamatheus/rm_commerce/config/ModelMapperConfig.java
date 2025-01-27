package com.imrochamatheus.rm_commerce.config;

import com.imrochamatheus.rm_commerce.dto.CategoryDTO;
import com.imrochamatheus.rm_commerce.dto.ProductDTO;
import com.imrochamatheus.rm_commerce.model.Category;
import com.imrochamatheus.rm_commerce.model.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    private void addProductConverter (ModelMapper modelMapper) {
        Converter<Set<Category>, Set<CategoryDTO>> categoriesConverter = ctx -> {
            return ctx.getSource()
                    .stream()
                    .map(cat -> modelMapper.map(cat, CategoryDTO.class))
                    .collect(Collectors.toSet());
        };

        modelMapper.createTypeMap(Product.class, ProductDTO.class)
                .addMappings(mapper ->
                        mapper.using(categoriesConverter).map(Product::getCategories, ProductDTO::addCategories));
    }

    @Bean
    public ModelMapper modelMapper () {
        ModelMapper modelMapper = new ModelMapper();
        this.addProductConverter(modelMapper);

        return modelMapper;
    }
}
