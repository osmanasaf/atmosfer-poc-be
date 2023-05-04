package org.codefirst.seed.userservice.dto;

import lombok.Data;

@Data
public class AdminGetTokenDto {
    private String username;
    private String password;
}
