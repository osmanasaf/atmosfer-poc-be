package org.codefirst.seed.userservice.service;

import lombok.AllArgsConstructor;
import org.codefirst.seed.userservice.dto.AdminRegisterDto;
import org.codefirst.seed.userservice.dto.GetOtpDto;
import org.codefirst.seed.userservice.dto.LdapUser;
import org.codefirst.seed.userservice.dto.LoginDto;
import org.codefirst.seed.userservice.entity.OneTimePassword;
import org.codefirst.seed.userservice.entity.RegisterRecord;
import org.codefirst.seed.userservice.repository.RegisterRepository;
import org.codefirst.seed.userservice.util.CryptUtil;
import org.codefirst.seed.userservice.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final OneTimePasswordService oneTimePasswordService;
    private final RegisterRepository registerRepository;
    private final LdapService ldapService;
    private final MailService mailService;
    private final JwtTokenUtil jwtTokenUtil;

   public RegisterRecord registerAdminPreRecord(AdminRegisterDto dto) {
       throwIfMailNotFitForRegister(dto.getMail());
       RegisterRecord registerRecord = new RegisterRecord(dto);
       mailService.sendAdminVerificationMail(registerRecord);
       return registerRepository.save(registerRecord);
   }
   public RegisterRecord registerAdminVerification(String mail, String code) {
       RegisterRecord registerRecord = registerRepository.findByMail(mail);
       if(registerRecord == null) {
           throw new RuntimeException("No Register Record found for email :" + mail);
       }
       if(registerRecord.getCode().equals(code)) {
           ldapService.create(registerRecord);
       } else {
           throw new RuntimeException("Validation code is not correct");
       }
       if(!DateUtil.isGivenDateInTheLast2Minutes(registerRecord.getDate())) {
           throw new RuntimeException("Register operation timeout, please try again");
       }
       return registerRecord;
   }
   public void deleteRegisterRecord(RegisterRecord registerRecord) {
        registerRepository.delete(registerRecord);
    }
   public void smsValidation(GetOtpDto dto) {
        LdapUser ldapUser = ldapService.getLdapUser(dto.getEmail());
        if(CryptUtil.matches(dto.getPassword(), ldapUser.getPassword())) {
            String otp = oneTimePasswordService.createOTP(ldapUser.getMail());
            mailService.sendOtpMail(dto.getEmail(), otp);
        } else {
            throw new RuntimeException("Bad Credentials");
        }
    }
    public String login(LoginDto dto) {
        LdapUser ldapUser = ldapService.getLdapUser(dto.getUsername());
        OneTimePassword tokenByEmail = oneTimePasswordService.getTokenByEmail(ldapUser.getMail());
        if(tokenByEmail == null) {
            throw new RuntimeException("Please get an otp first");
        }
        if(!DateUtil.isGivenDateInTheLast2Minutes(tokenByEmail.getRequestDate())) {
            oneTimePasswordService.delete(tokenByEmail);
            throw new RuntimeException("otp timeout, please get new one");
        }
        boolean allCredentialsAreCorrect = CryptUtil.matches(dto.getPassword(), ldapUser.getPassword())
                && tokenByEmail.getOtp().equals(dto.getOtp());
        if(allCredentialsAreCorrect) {
            return generateJwtToken(ldapUser);
        }
        throw new RuntimeException("Bad Credentials");
    }
    public String generateJwtToken(LdapUser ldapUser) {
        return jwtTokenUtil.generateToken(ldapUser);
    }
    private void throwIfMailNotFitForRegister(String mail) {
        RegisterRecord registerRecord = registerRepository.findByMail(mail);
        if(registerRecord != null) {
            if(DateUtil.isGivenDateInTheLast2Minutes(registerRecord.getDate())) {
                throw new RuntimeException("Verification Mail already sent, please check your email");
            } else {
                registerRepository.delete(registerRecord);
            }
        }

        List<LdapUser> users = ldapService.search(mail);
        if(users != null && users.size() > 0) {
            throw new RuntimeException("this mail already taken");
        }
    }
}
