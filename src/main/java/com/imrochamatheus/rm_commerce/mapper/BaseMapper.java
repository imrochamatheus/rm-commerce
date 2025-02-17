package com.imrochamatheus.rm_commerce.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseMapper <D, E>{

    @Autowired
    private ModelMapper modelMapper;

    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    public BaseMapper(Class<D> dtoClass, Class<E> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public D toDTO (E entity) {
        return modelMapper.map(entity, dtoClass);
    }

    public E fromDTO (D dto) {
        return modelMapper.map(dto, entityClass);
    }
}
