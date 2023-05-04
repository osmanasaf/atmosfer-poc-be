package org.codefirst.seed.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.producer.topic}")
    private String topicName;
    public void sendMessage(String msg, String user, String params) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        String text = timeStamp + ";" + user + ";" + msg + ";" + params;
        kafkaTemplate.send(topicName, text);
    }
}
