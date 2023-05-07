package org.atmosfer.seed.logservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final LogService logService;

    @KafkaListener(topics = "logs", groupId = "service")
    public void listenGroupFoo(String message) {
        logService.save(message);
    }
}
