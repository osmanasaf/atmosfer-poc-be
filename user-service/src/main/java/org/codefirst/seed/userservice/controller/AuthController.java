package org.codefirst.seed.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.*;
import org.codefirst.seed.userservice.service.JwtTokenUtil;
import org.codefirst.seed.userservice.service.KafkaService;
import org.codefirst.seed.userservice.service.LdapService;
import org.codefirst.seed.userservice.type.AdminType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final LdapService ldapService;
    private final KafkaService kafkaService;
    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping("/admin/exist/{username}")
    public AdminExistDto isAdminExist(@PathVariable("username") String username) {
        List<LdapUser> admins = ldapService.search(username);
        AdminExistDto dto = new AdminExistDto();
        dto.setUsername(username);
        dto.setExist(admins.size() > 0);

        kafkaService.sendMessage("isAdminExist", null, username);
        return dto;
    }

    @PostMapping("/admin/register")
    public void registerAdmin(@RequestBody AdminRegisterDto dto) {
        ldapService.create(dto);
    }

    @PutMapping("/admin/modify/username/{username}/ou/{ou}")
    public void modifyAdmin(@PathVariable String username, @PathVariable AdminType ou) {
        ldapService.modify(username, ou);
    }

    @PostMapping("/admin/get-token")
    public TokenDto getAdminToken(@RequestBody AdminGetTokenDto dto) {
        return new TokenDto(jwtTokenUtil.generateToken(dto));
    }
}
