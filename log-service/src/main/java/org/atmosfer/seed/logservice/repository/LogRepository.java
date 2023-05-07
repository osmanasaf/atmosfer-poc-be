package org.atmosfer.seed.logservice.repository;

import org.atmosfer.seed.logservice.data.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
}