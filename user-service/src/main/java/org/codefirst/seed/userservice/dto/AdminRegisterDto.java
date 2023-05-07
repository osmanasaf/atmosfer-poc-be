package org.codefirst.seed.userservice.dto;

import lombok.Data;

@Data
public class AdminRegisterDto {
    private String name;
    private String surname;
    private String mail;
    private String msisdn;
    private String password;
}
