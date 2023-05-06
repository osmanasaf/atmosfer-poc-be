package org.codefirst.seed.businessservice.repository;

import org.codefirst.seed.businessservice.data.Position;
import org.codefirst.seed.businessservice.type.PositionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends CrudRepository<Position, String > {

    List<Position> findAllByPositionStatus(PositionStatus positionStatus);

    List<Position> findAllByPositionStatusNot(PositionStatus positionStatus);
}
