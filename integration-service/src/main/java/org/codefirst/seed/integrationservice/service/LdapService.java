package org.codefirst.seed.integrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapService {

    private final LdapTemplate ldapTemplate;

    @PostConstruct
    private void doJob() {
        search("admin").forEach(System.out::println);
    }

    public List<String> search(String username) {
        return ldapTemplate
                .search(
                        "ou=users",
                        "cn=" + username,
                        (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }
}
