package org.atmosfer.seed.businessservice.repository;

import org.atmosfer.seed.businessservice.data.Position;
import org.atmosfer.seed.businessservice.type.PositionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends CrudRepository<Position, String > {

    List<Position> findAllByPositionStatus(PositionStatus positionStatus);

    List<Position> findAllByPositionStatusNot(PositionStatus positionStatus);
}
