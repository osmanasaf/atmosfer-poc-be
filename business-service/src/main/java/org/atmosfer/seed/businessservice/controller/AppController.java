package org.atmosfer.seed.businessservice.controller;

import lombok.AllArgsConstructor;
import org.atmosfer.seed.businessservice.feignClient.UserClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app")
@AllArgsConstructor
public class AppController {

    private final UserClient userClient;
    @GetMapping("data")
    public String getData() {
        return userClient.getByUsername("mahmut").getId().toString();
    }
}
