package org.atmosfer.seed.logservice.service;

import lombok.RequiredArgsConstructor;
import org.atmosfer.seed.logservice.repository.LogRepository;
import org.atmosfer.seed.logservice.data.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    @PostConstruct
    private void doJob() {
        //save("data");
        logRepository.findAll().forEach(System.out::println);
    }

    public void save(String msg) {
        Log log = new Log();
        log.setData(msg);
        logRepository.save(log);
    }
}
