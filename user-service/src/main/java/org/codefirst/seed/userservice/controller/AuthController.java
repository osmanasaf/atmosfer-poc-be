package org.codefirst.seed.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.*;
import org.codefirst.seed.userservice.entity.RegisterRecord;
import org.codefirst.seed.userservice.service.KafkaService;
import org.codefirst.seed.userservice.service.LdapService;
import org.codefirst.seed.userservice.service.UserService;
import org.codefirst.seed.userservice.type.UserRole;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapService ldapService;
    private final UserService userService;
    private final KafkaService kafkaService;

    @GetMapping("/exist/{username}")
    public AdminExistDto isUserExist(@PathVariable("username") String username) {
        return ldapService.isExist(username);
    }

    @PostMapping("/register-first-step")
    public void register(@RequestBody AdminRegisterDto dto) {
        userService.registerAdminPreRecord(dto);
        kafkaService.sendMessage("register", dto.getMail(), dto.toString());
    }

    @PutMapping("/register-verification/mail/{mail}/code/{code}")
    public String verification(@PathVariable String mail, @PathVariable String code) {
        RegisterRecord registerRecord = userService.registerAdminVerification(mail, code);
        String jwt = userService.generateJwtToken(ldapService.search(mail).get(0));
        userService.deleteRegisterRecord(registerRecord);
        kafkaService.sendMessage("register-verification", mail, code);
        return jwt;
    }
    @PostMapping("/login-first-step")
    public void getOtp(@RequestBody GetOtpDto dto) {
        userService.smsValidation(dto);
        kafkaService.sendMessage("get-otp", dto.getEmail(), dto.toString());
    }
    @PostMapping("/login-verification")
    public String login(@RequestBody LoginDto dto) {
        String jwt = userService.login(dto);
        kafkaService.sendMessage("login", dto.getUsername(), dto.toString());
        return jwt;
    }

    @PutMapping("/modify/username/{username}/ou/{ou}")
    public void modifyRole(@PathVariable String username, @PathVariable UserRole ou) {
        ldapService.modifyRole(username, ou);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordDto dto) {
        ldapService.changePassword(dto);
    }

    @PostMapping("forgot-password")
    public void forgotPassword(@RequestParam String username) {
        ldapService.forgotPassword(username);
    }

    @PostMapping("forgot-password-link")
    public void forgotPasswordMail(@RequestParam String username) {
        ldapService.sendForgotPasswordLink(username);
    }
}
