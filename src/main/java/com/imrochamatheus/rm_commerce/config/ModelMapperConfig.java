package com.imrochamatheus.rm_commerce.config;

import com.imrochamatheus.rm_commerce.dto.*;
import com.imrochamatheus.rm_commerce.model.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
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

        Converter<Set<CategoryDTO>, Set<Category>> categoriesDTOConverter = ctx -> {
            return ctx.getSource()
                .stream()
                .map(cat -> modelMapper.map(cat, Category.class))
                .collect(Collectors.toSet());
        };

        modelMapper.createTypeMap(ProductDTO.class, Product.class)
                .addMappings(mapper ->
                    mapper.using(categoriesDTOConverter).map(ProductDTO::getCategories, Product::addCategories));

        modelMapper.createTypeMap(Product.class, ProductDTO.class)
                .addMappings(mapper ->
                    mapper.using(categoriesConverter).map(Product::getCategories, ProductDTO::addCategories));
    }

    private void addOrderConverter (ModelMapper modelMapper) {
        Converter<Set<OrderItem>, List<OrderItemDTO>> orderItemConverter = ctx ->
                ctx.getSource()
                    .stream()
                    .map(orderItem -> {
                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                        orderItemDTO.setPrice(orderItem.getPrice());
                        orderItemDTO.setQuantity(orderItem.getQuantity());
                        orderItemDTO.setName(orderItem.getProduct().getName());
                        orderItemDTO.setProductId(orderItem.getProduct().getId());
                        orderItemDTO.setImgUrl(orderItem.getProduct().getImgUrl());

                        return orderItemDTO;
                    })
                    .collect(Collectors.toList());

        modelMapper.createTypeMap(Order.class, OrderDTO.class)
            .addMappings(mapper -> {
                mapper.using(orderItemConverter).map(Order::getItems, OrderDTO::setItems);
            });
    }

    private void addUserConverter (ModelMapper modelMapper) {
        modelMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(mapper ->
                        mapper.skip(UserDTO::setPassword));
    }

    @Bean
    public ModelMapper modelMapper () {
        ModelMapper modelMapper = new ModelMapper();
        this.addProductConverter(modelMapper);
        this.addUserConverter(modelMapper);
        this.addOrderConverter(modelMapper);

        return modelMapper;
    }
}
