package org.atmosfer.seed.userservice.repository;

import org.atmosfer.seed.userservice.entity.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    OneTimePassword findByEmail(String email);
}
