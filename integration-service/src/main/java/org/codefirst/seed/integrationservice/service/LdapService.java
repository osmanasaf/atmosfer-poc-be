package org.codefirst.seed.integrationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.naming.Name;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapService {

    private final LdapTemplate ldapTemplate;

    @PostConstruct
    private void doJob() {
        create("user", "password");
        search("user").forEach(System.out::println);
    }

    public List<String> search(String username) {
        return ldapTemplate
                .search(
                        "ou=newusers",
                        "cn=" + username,
                        (AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
    }

    public void create(String username, String password) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "newusers")
                .add("cn", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson" });
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("userPassword", password);

        ldapTemplate.bind(context);
    }
}
