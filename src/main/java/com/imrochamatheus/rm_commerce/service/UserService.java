package com.imrochamatheus.rm_commerce.service;

import com.imrochamatheus.rm_commerce.dto.UserDTO;
import com.imrochamatheus.rm_commerce.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    private UserDTO toDTO (User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    private User fromDTO (UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }
}
