package org.codefirst.seed.businessservice.repository;

import org.codefirst.seed.businessservice.data.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends CrudRepository<Position, String > {
}
