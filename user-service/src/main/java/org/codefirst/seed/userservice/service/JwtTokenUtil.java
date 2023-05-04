package org.codefirst.seed.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.AdminGetTokenDto;
import org.codefirst.seed.userservice.dto.LdapUser;
import org.codefirst.seed.userservice.util.CryptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    private final LdapService ldapService;
    public String generateToken(AdminGetTokenDto dto) {
        LdapUser ldapUser = ldapService.search(dto.getUsername()).get(0);
        if(CryptUtil.matches(dto.getPassword(), ldapUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", ldapUser.getOu());
            return doGenerateToken(claims, dto.getUsername());
        }
        throw new RuntimeException("Bad Credentials");
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


}
