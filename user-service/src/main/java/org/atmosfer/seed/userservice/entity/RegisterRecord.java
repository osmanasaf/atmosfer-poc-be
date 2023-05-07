package org.atmosfer.seed.userservice.entity;

import lombok.Data;
import org.atmosfer.seed.userservice.dto.AdminRegisterDto;
import org.atmosfer.seed.userservice.dto.AppUserRegisterDto;
import org.atmosfer.seed.userservice.util.RandomGeneratorUtil;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class RegisterRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String msisdn;
    private String password;
    private String code;
    private Date date;


    public RegisterRecord(AdminRegisterDto dto) {
        this.setName(dto.getName());
        this.setMail(dto.getMail());
        this.setPassword(dto.getPassword());
        this.setMsisdn(dto.getMsisdn());
        this.setName(dto.getName());
        this.setSurname(dto.getSurname());
        this.setDate(new Date());
        this.code = RandomGeneratorUtil.generateOtp();
    }

    public RegisterRecord(AppUserRegisterDto dto) {
        this.setMail(dto.getEmail());
        this.setPassword(dto.getPassword());
        this.code = RandomGeneratorUtil.generateOtp();
        this.setDate(new Date());
    }
    public RegisterRecord() {
    }
}
