package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.*;
import org.codefirst.seed.userservice.entity.OneTimePassword;
import org.codefirst.seed.userservice.entity.PasswordResetToken;
import org.codefirst.seed.userservice.type.UserRole;
import org.codefirst.seed.userservice.util.CryptUtil;
import org.codefirst.seed.userservice.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LdapService {

    private final LdapTemplate ldapTemplate;
    private final MailService mailService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final KafkaService kafkaService;
    private final OneTimePasswordService oneTimePasswordService;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${ldap.partitionSuffix}")
    private String ldapSuffix;

    public List<LdapUser> search(String username) {
        return ldapTemplate
                .search("", "cn=" + username, LdapUser::createFromAttrs);
    }

    public UserRole getAdminUserType(String username) {
        return search(username).get(0).getOu();
    }

    public void create(AdminRegisterDto dto) {
        Name dn = buildUserDn(dto.getUsername());
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
        context.setAttributeValue("description", UserRole.NEW_USER.name());

        ldapTemplate.bind(context);
    }

    public void modify(String username, UserRole ou) {
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

    public LdapUser getLdapUser(String username) {
        List<LdapUser> ldapUsers = search(username);
        if(ldapUsers.size() == 0) {
            throw new RuntimeException("User not found");
        }
        return ldapUsers.get(0);
    }

    public void compareOldPassword(String ldapPassword, String password) {
        boolean isEqual = ldapPassword.equals(password);
        if(!isEqual) {
            throw new RuntimeException("Password is not correct");
        }
    }

    public void compareNewPassword(String newPassword, String oldPassword) {
        boolean isEqual = newPassword.equals(oldPassword);
        if(isEqual) {
            throw new RuntimeException("New password cannot be same with old password");
        }
    }

    public void changePassword(ChangePasswordDto changePasswordDto) {
        LdapUser ldapUser = getLdapUser(changePasswordDto.getUsername());
        compareOldPassword(CryptUtil.encode(changePasswordDto.getOldPassword()), ldapUser.getPassword());
        compareNewPassword(changePasswordDto.getNewPassword(), changePasswordDto.getOldPassword());
        updatePasswordOnLdap(ldapUser.getCn(), changePasswordDto.getNewPassword());
    }


    public void updatePasswordOnLdap(String username, String newPassword) {
        Name dn = buildUserDn(username);
        ModificationItem[] modificationItems = new ModificationItem[1];
        modificationItems[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", CryptUtil.encode(newPassword)));
        ldapTemplate.modifyAttributes(dn, modificationItems);
        kafkaService.sendMessage("updatePassword", null, username);
    }

    private Name buildUserDn(String username) {
        //neden rolü ou ile belirlemiyoruz??
        return LdapNameBuilder.newInstance()
                .add("ou", "newusers")
                .add("cn", username)
                .build();
    }

    public void forgotPassword(String username) {
        LdapUser ldapUser = getLdapUser(username);
        String generatedRandomPassword = RandomGenerator.generateRandomPassword();
        mailService.sendForgotPasswordMail(ldapUser.getMail(), generatedRandomPassword);
        kafkaService.sendMessage("sendNewPasswordLink", null, username);
        updatePasswordOnLdap(ldapUser.getCn(), CryptUtil.encode(generatedRandomPassword));
    }

    public void sendForgotPasswordLink(String username) {
        LdapUser ldapUser = getLdapUser(username);
        PasswordResetToken resetPasswordToken = passwordResetTokenService.createResetPasswordToken(ldapUser);
        String restPasswordLink = passwordResetTokenService.generateResetPasswordTokenLink(resetPasswordToken.getToken());
        mailService.sendResetPasswordLink(ldapUser.getMail(), restPasswordLink);
      //  kafkaService.sendMessage("sendForgotPasswordLink", null, username);
    }

    public AdminExistDto isExist(String username) {
        List<LdapUser> users = search(username);
        AdminExistDto dto = new AdminExistDto();
        dto.setUsername(username);
        dto.setExist(users.size() > 0);
        //kafkaService.sendMessage("isAdminExist", null, username);
        return dto;
    }

    public String smsValidation(GetOtpDto dto) {
        LdapUser ldapUser = getLdapUser(dto.getEmail());
        if(CryptUtil.matches(dto.getPassword(), ldapUser.getPassword())) {
            return oneTimePasswordService.createOTP(ldapUser.getMail());
        }
        throw new RuntimeException("Bad Credentials");
    }

    public String login(LoginDto dto) {
        LdapUser ldapUser = getLdapUser(dto.getUsername());
        Optional<OneTimePassword> tokenByEmail = oneTimePasswordService.getTokenByEmail(ldapUser.getMail());
        boolean allCredentialsAreCorrect = CryptUtil.matches(dto.getPassword(), ldapUser.getPassword()) && tokenByEmail.isPresent() && tokenByEmail.get().getOtp().equals(dto.getOtp());
        if(allCredentialsAreCorrect) {
          return jwtTokenUtil.generateToken(ldapUser);
        }
        throw new RuntimeException("Bad Credentials");
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
