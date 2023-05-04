package org.codefirst.seed.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.AdminExistDto;
import org.codefirst.seed.userservice.service.LdapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapService ldapService;

    @GetMapping("/admin/exist/{username}")
    public AdminExistDto isAdminExist(@PathVariable("username") String username) {
        List<String> admins = ldapService.search(username);
        AdminExistDto dto = new AdminExistDto();
        dto.setUsername(username);
        dto.setExist(admins.size() > 0);
        return dto;
    }
}
