package org.atmosfer.seed.userservice.dto;

import lombok.Data;

@Data
public class TokenDto {
    private String token;
    public TokenDto(String token) {
        this.token = token;
    }
}
