package org.atmosfer.seed.userservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.userservice.dto.LdapUser;
import org.atmosfer.seed.userservice.service.LdapService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final LdapService ldapService;

    @GetMapping("all-dealer-employee")
    public List<LdapUser> gelAllDealerEmployee(){
        return ldapService.getAllDealerEmployee();
    }
}
