package org.codefirst.seed.businessservice.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
}
