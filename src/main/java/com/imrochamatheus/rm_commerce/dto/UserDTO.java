package com.imrochamatheus.rm_commerce.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDate birthDate;
}