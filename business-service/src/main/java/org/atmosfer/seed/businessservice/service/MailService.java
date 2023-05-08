package org.atmosfer.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.businessservice.dto.EmailDetailsDto;
import org.atmosfer.seed.businessservice.type.ApprovalStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendHrInfoMail(String mailTo, String positionName, ApprovalStatus status){
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(mailTo)
                .subject("Bilgilendirme")
                .msgBody(positionName + "pozisyonu için ik ekipleri tarafından başvurunuza yapılan değerlendirme sonucu : " + status)
                .build();
        sendMail(emailDetailsDto);
    }

    public void sendTechnicalInfoMail(String mailTo, String positionName, ApprovalStatus status){
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(mailTo)
                .subject("Bilgilendirme")
                .msgBody(positionName + "pozisyonu için teknik ekipleri tarafından başvurunuza yapılan değerlendirme sonucu : " + status)
                .build();
        sendMail(emailDetailsDto);
    }

    public void sendPricingInfoMail(String mailTo, String positionName, ApprovalStatus status){
        EmailDetailsDto emailDetailsDto = EmailDetailsDto.builder()
                .recipient(mailTo)
                .subject("Bilgilendirme")
                .msgBody(positionName + "pozisyonu için muhasebe ekipleri tarafından başvurunuza yapılan değerlendirme sonucu : " + status)
                .build();
        sendMail(emailDetailsDto);
    }

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
}
