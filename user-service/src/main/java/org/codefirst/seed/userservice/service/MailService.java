package org.codefirst.seed.userservice.service;

import org.codefirst.seed.userservice.dto.EmailDetailsDto;
import org.codefirst.seed.userservice.entity.RegisterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    @Autowired
    private  JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(EmailDetailsDto details) {
        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Mail could not send", e);
        }
    }

    public void sendForgotPasswordMail(String mail, String generatedRandomPassword) {
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(mail)
                .subject("Yeni şifreniz")
                .msgBody("Yeni şifreniz: " + generatedRandomPassword)
                .build();
        sendMail(emailDetailsDto);
    }

    public void sendResetPasswordLink(String mail, String restPasswordLink) {
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(mail)
                .subject("Şifre sıfırlama")
                .msgBody("Şifrenizi sıfırlamak için linke tıklayınız: " + restPasswordLink)
                .build();
        sendMail(emailDetailsDto);
    }

    public void sendAdminVerificationMail(RegisterRecord registerRecord) {
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(registerRecord.getMail())
                .subject("Hesap Doğrulama")
                .msgBody("Hesabınızı doğrulamak için linke tıklayınız: " + registerRecord.getCode())
                .build();
        sendMail(emailDetailsDto);
    }

    public void sendOtpMail(String mail, String otp) {
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder().recipient(mail)
                .subject("Otp")
                .msgBody("Hesabınıza girmek için bu kodu kullanabilirsiniz: " + otp)
                .build();
        sendMail(emailDetailsDto);
    }
}
