package org.codefirst.seed.userservice.repository;

import org.codefirst.seed.userservice.entity.OneTimePassword;
import org.codefirst.seed.userservice.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    OneTimePassword findByEmail(String email);
}
