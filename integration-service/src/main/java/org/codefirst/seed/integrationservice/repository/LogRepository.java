package org.codefirst.seed.integrationservice.repository;

import org.codefirst.seed.integrationservice.data.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
}
