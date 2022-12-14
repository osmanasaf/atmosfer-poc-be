package org.codefirst.seed.businessservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app")
public class AppController {

    @GetMapping("data")
    public String getData() {
        return "data";
    }
}
