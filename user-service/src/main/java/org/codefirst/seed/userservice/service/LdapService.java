package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.AdminRegisterDto;
import org.codefirst.seed.userservice.dto.LdapUser;
import org.codefirst.seed.userservice.type.AdminType;
import org.codefirst.seed.userservice.util.CryptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapService {

    private final LdapTemplate ldapTemplate;
    @Value("${ldap.partitionSuffix}")
    private String ldapSuffix;

    public List<LdapUser> search(String username) {
        return ldapTemplate
                .search("", "cn=" + username, LdapUser::createFromAttrs);
    }

    public AdminType getAdminUserType(String username) {
        return search(username).get(0).getOu();
    }

    public void create(AdminRegisterDto dto) {
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "newusers")
                .add("cn", dto.getUsername())
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson" });
        context.setAttributeValue("cn", dto.getUsername());
        context.setAttributeValue("givenname", dto.getName());
        context.setAttributeValue("sn", dto.getSurname());
        context.setAttributeValue("userPassword", CryptUtil.encode(dto.getPassword()));
        context.setAttributeValue("mail", dto.getMail());
        context.setAttributeValue("mobile", dto.getMsisdn());
        context.setAttributeValue("description", AdminType.NEW_USER.name());

        ldapTemplate.bind(context);
    }

    public void modify(String username, AdminType ou) {
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", "newusers")
                .add("cn", username)
                .build();
        DirContextOperations context
                = ldapTemplate.lookupContext(dn);

        context.setAttributeValues
                ("objectclass",
                        new String[]
                                { "top",
                                        "person",
                                        "organizationalPerson",
                                        "inetOrgPerson" });
        context.setAttributeValue("description", ou.name());

        ldapTemplate.modifyAttributes(context);
    }
}
