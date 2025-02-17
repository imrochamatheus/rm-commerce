package com.imrochamatheus.rm_commerce.mapper;

import com.imrochamatheus.rm_commerce.dto.UserDTO;
import com.imrochamatheus.rm_commerce.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<UserDTO, User>{

   public UserMapper () {
       super(UserDTO.class, User.class);
   }
}
