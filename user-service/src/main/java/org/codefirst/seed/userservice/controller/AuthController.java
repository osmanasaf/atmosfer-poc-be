package org.codefirst.seed.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.*;
import org.codefirst.seed.userservice.service.LdapService;
import org.codefirst.seed.userservice.type.UserRole;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapService ldapService;

    @GetMapping("/exist/{username}")
    public AdminExistDto isUserExist(@PathVariable("username") String username) {
        return ldapService.isExist(username);
    }

    @PostMapping("/register")
    public void register(@RequestBody AdminRegisterDto dto) {
        ldapService.create(dto);
    }

    @PutMapping("/modify/username/{username}/ou/{ou}")
    public void modifyRole(@PathVariable String username, @PathVariable UserRole ou) {
        ldapService.modify(username, ou);
    }

    @PostMapping("/otp")
    public String smsValidation(@RequestBody GetOtpDto dto) {
        return ldapService.smsValidation(dto);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto) {
        return ldapService.login(dto);
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
