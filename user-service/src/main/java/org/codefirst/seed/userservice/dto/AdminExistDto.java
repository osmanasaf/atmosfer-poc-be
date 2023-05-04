package org.codefirst.seed.userservice.dto;

import lombok.Data;

@Data
public class AdminExistDto {
    private String username;
    private boolean isExist;
}
