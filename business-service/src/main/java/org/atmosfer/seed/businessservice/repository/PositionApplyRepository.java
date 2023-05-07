package org.atmosfer.seed.businessservice.repository;

import org.atmosfer.seed.businessservice.data.PositionApply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionApplyRepository extends CrudRepository<PositionApply, String> {
    PositionApply findByEmail(String email);
    List<PositionApply> findAllByPositionId(String positionId);
}
