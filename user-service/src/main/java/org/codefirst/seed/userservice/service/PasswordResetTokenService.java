package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.dto.LdapUser;
import org.codefirst.seed.userservice.entity.PasswordResetToken;
import org.codefirst.seed.userservice.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${base.url}")
    private String baseApiUrl;
    private PasswordResetToken getResetPasswordToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken createResetPasswordToken(LdapUser ldapUser) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(2);
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .email(ldapUser.getMail())
                .token(token)
                .expiryDate(expiryDate)
                .build();
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public String generateResetPasswordTokenLink(String token) {
        return baseApiUrl+  "/reset-password?token=" + token;
    }

}



