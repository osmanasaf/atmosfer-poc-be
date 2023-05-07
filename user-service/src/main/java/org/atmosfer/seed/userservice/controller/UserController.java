package org.atmosfer.seed.userservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.userservice.dto.ChangePasswordDto;
import org.atmosfer.seed.userservice.dto.LdapUser;
import org.atmosfer.seed.userservice.service.LdapService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final LdapService ldapService;

    /***/@PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordDto dto) {
        ldapService.changePassword(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()), dto);
    }

    @GetMapping("all-dealer-employee")
    public List<LdapUser> gelAllDealerEmployee() {
        return ldapService.getAllDealerEmployee();
    }
}
