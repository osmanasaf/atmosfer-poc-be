package org.codefirst.seed.businessservice.service;

import lombok.RequiredArgsConstructor;
import org.codefirst.seed.businessservice.data.Position;
import org.codefirst.seed.businessservice.repository.PositionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    @PostConstruct
    private void doJob() {
        Position position = new Position();
        position.setName("deneme");
        position.setDetail("detail");
        Position fromRepo = positionRepository.save(position);
        System.out.println(fromRepo);
    }
}
