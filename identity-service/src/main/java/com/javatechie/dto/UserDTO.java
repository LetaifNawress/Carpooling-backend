package com.javatechie.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private byte[] profileImage;

    private String roleName;
}
