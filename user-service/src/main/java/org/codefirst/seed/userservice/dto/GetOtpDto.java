package org.codefirst.seed.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class GetOtpDto {
    private String email;
    private String password;
}
