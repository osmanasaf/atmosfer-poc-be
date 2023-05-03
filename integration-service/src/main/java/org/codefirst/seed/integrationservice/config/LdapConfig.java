package org.codefirst.seed.integrationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

@Component
public class LdapConfig {
    @Value("${ldap.url}")
    private String ldapUrl;
    @Value("${ldap.partitionSuffix}")
    private String ldapSuffix;
    @Value("${ldap.principal}")
    private String ldapUser;
    @Value("${ldap.password}")
    private String ldapPassword;
    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapSuffix);
        contextSource.setUserDn(ldapUser);
        contextSource.setPassword(ldapPassword);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
