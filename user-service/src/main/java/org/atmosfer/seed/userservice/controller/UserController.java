package org.atmosfer.seed.userservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.userservice.dto.ChangePasswordDto;
import org.atmosfer.seed.userservice.dto.LdapUser;
import org.atmosfer.seed.userservice.service.LdapService;
import org.atmosfer.seed.userservice.type.UserRole;
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

    /***/@PutMapping("/modify/username/{username}/ou/{ou}")
    public void modifyRole(@PathVariable String username, @PathVariable UserRole ou) {
        String currUser = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if(!ldapService.getAdminUserType(currUser).equals(UserRole.ADMIN)) {
            throw new RuntimeException("NOT ALLOWED");
        }
        ldapService.modifyRole(username, ou);
    }

    /***/@GetMapping("all-dealer-employee")
    public List<LdapUser> gelAllDealerEmployee() {
        return ldapService.getAllDealerEmployee();
    }
}
