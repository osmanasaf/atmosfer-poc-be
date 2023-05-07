package org.codefirst.seed.userservice.repository;

import org.codefirst.seed.userservice.entity.RegisterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterRecord, Long> {
    public RegisterRecord findByMail(String mail);
}
