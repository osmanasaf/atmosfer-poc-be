package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.entity.OneTimePassword;
import org.codefirst.seed.userservice.repository.OneTimePasswordRepository;
import org.codefirst.seed.userservice.util.RandomGeneratorUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneTimePasswordService {

    private final OneTimePasswordRepository oneTimePasswordRepository;

    public String createOTP(String email){
        OneTimePassword oneTimePassword1 = getTokenByEmail(email);
        if (oneTimePassword1 != null) {
            oneTimePasswordRepository.delete(oneTimePassword1);
        }

        String otp = RandomGeneratorUtil.generateOtp();
        OneTimePassword oneTimePassword = OneTimePassword
                .builder()
                .email(email)
                .requestDate(new Date())
                .otp(otp).build();
        oneTimePasswordRepository.save(oneTimePassword);
        return otp;
    }

    public OneTimePassword getTokenByEmail(String email){
        return oneTimePasswordRepository.findByEmail(email);
    }

    public void delete(OneTimePassword oneTimePassword) {
        oneTimePasswordRepository.delete(oneTimePassword);
    }
}
