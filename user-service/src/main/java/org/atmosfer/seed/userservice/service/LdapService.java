package org.atmosfer.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.userservice.dto.AdminExistDto;
import org.atmosfer.seed.userservice.dto.ChangePasswordDto;
import org.atmosfer.seed.userservice.dto.LdapUser;
import org.atmosfer.seed.userservice.entity.RegisterRecord;
import org.atmosfer.seed.userservice.type.UserRole;
import org.atmosfer.seed.userservice.util.CryptUtil;
import org.atmosfer.seed.userservice.util.RandomGeneratorUtil;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LdapService {

    private final LdapTemplate ldapTemplate;
    private final MailService mailService;

    public List<LdapUser> search(String username) {
        return ldapTemplate
                .search("", "cn=" + username, LdapUser::createFromAttrs);
    }

    public UserRole getAdminUserType(String username) {
        return search(username).get(0).getOu();
    }

    public void createAdmin(RegisterRecord registerRecord) {
        Name dn = buildUserDn(registerRecord.getMail());
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson" });
        context.setAttributeValue("cn", registerRecord.getMail());
        context.setAttributeValue("givenname", registerRecord.getName());
        context.setAttributeValue("sn", registerRecord.getSurname());
        context.setAttributeValue("userPassword", CryptUtil.encode(registerRecord.getPassword()));
        context.setAttributeValue("mail", registerRecord.getMail());
        context.setAttributeValue("mobile", registerRecord.getMsisdn());
        context.setAttributeValue("description", UserRole.NEW_USER.name());

        ldapTemplate.bind(context);
    }

    public void createAppUser(RegisterRecord registerRecord) {
        Name dn = buildUserDn(registerRecord.getMail());
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson" });
        context.setAttributeValue("cn", registerRecord.getMail());
        context.setAttributeValue("sn","unknown");
        context.setAttributeValue("userPassword", CryptUtil.encode(registerRecord.getPassword()));
        context.setAttributeValue("mail", registerRecord.getMail());
        context.setAttributeValue("description", UserRole.APPUSER.name());

        ldapTemplate.bind(context);
    }

    public void modifyRole(String username, UserRole ou) {
        Name dn = buildUserDn(username);
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
    public void modifyPassword(String username, String newPassword) {
        Name dn = buildUserDn(username);
        DirContextOperations context
                = ldapTemplate.lookupContext(dn);

        context.setAttributeValues
                ("objectclass",
                        new String[]
                                { "top",
                                        "person",
                                        "organizationalPerson",
                                        "inetOrgPerson" });
        context.setAttributeValue("userPassword", CryptUtil.encode(newPassword));

        ldapTemplate.modifyAttributes(context);
    }

    public LdapUser getLdapUser(String username) {
        List<LdapUser> ldapUsers = search(username);
        if(ldapUsers.size() == 0) {
            throw new RuntimeException("User not found");
        }
        return ldapUsers.get(0);
    }
    public void compareNewPassword(String newPassword, String oldPassword) {
        boolean isEqual = newPassword.equals(oldPassword);
        if(isEqual) {
            throw new RuntimeException("New password cannot be same with old password");
        }
    }

    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
        LdapUser ldapUser = getLdapUser(username);
        if(!CryptUtil.matches(changePasswordDto.getOldPassword(), ldapUser.getPassword())) {
            throw new RuntimeException("Password is not correct");
        }
        compareNewPassword(changePasswordDto.getNewPassword(), changePasswordDto.getOldPassword());
        modifyPassword(username, changePasswordDto.getNewPassword());
    }

    private Name buildUserDn(String username) {
        return LdapNameBuilder.newInstance()
                .add("ou", "newusers")
                .add("cn", username)
                .build();
    }

    public void forgotPassword(String username) {
        LdapUser ldapUser = getLdapUser(username);
        String generatedRandomPassword = RandomGeneratorUtil.generateRandomPassword();
        modifyPassword(username, generatedRandomPassword);
        mailService.sendForgotPasswordMail(ldapUser.getMail(), generatedRandomPassword);
    }

    public AdminExistDto isExist(String username) {
        List<LdapUser> users = search(username);
        AdminExistDto dto = new AdminExistDto();
        dto.setUsername(username);
        dto.setExist(users.size() > 0);
        return dto;
    }
    public List<LdapUser> getAllDealerEmployee() {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("ou", "HR"));
        filter.and(new EqualsFilter("ou", "TECHNICAL"));
        filter.and(new EqualsFilter("ou", "PRICING"));
        return ldapTemplate
                .search("", filter.encode(), LdapUser::createFromAttrs);

    }
}
