package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.userservice.entity.OneTimePassword;
import org.codefirst.seed.userservice.repository.OneTimePasswordRepository;
import org.codefirst.seed.userservice.util.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OneTimePasswordService {

    private final OneTimePasswordRepository oneTimePasswordRepository;

    public String createOTP(String email){
        String otp = RandomGenerator.generateOtp();
        OneTimePassword oneTimePassword = OneTimePassword
                .builder()
                .email(email)
                .otp(otp).build();
        getTokenByEmail(email).ifPresent(oneTimePasswordRepository::delete);
        oneTimePasswordRepository.save(oneTimePassword);
        return otp;
    }

    public Optional<OneTimePassword> getTokenByEmail(String email){
        return oneTimePasswordRepository.findByEmail(email);
    }
}
