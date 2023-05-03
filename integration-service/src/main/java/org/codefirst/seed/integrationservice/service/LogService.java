package org.codefirst.seed.integrationservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.integrationservice.data.Log;
import org.codefirst.seed.integrationservice.repository.LogRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    @PostConstruct
    private void doJob() {
        Log log = new Log();
        log.setData("data");
        //logRepository.save(log);
        logRepository.findAll().forEach(System.out::println);
    }
}
