package org.codefirst.seed.userservice.dto;

import lombok.Data;
import org.codefirst.seed.userservice.type.AdminType;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

@Data
public class LdapUser {
    private String cn;
    private AdminType ou;
    private String password;
    private String mail;
    public static LdapUser createFromAttrs(Attributes attrs) {
        LdapUser ldapUser = new LdapUser();
        try {
            ldapUser.setCn(String.valueOf(attrs.get("cn").get()));
            Object o = attrs.get("userpassword").get();
            byte[] bytes = (byte[]) o;
            String hash = new String(bytes);
            ldapUser.setPassword(hash);
            ldapUser.setOu(AdminType.valueOf(String.valueOf(attrs.get("description").get())));
            ldapUser.setMail(String.valueOf(attrs.get("mail").get()));
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return ldapUser;
    }
}
