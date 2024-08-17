package com.offerista.producer.controller;

import com.offerista.producer.model.ProducerConfig;
import com.offerista.producer.service.ProducerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/start")
    public void start(@Valid @RequestBody ProducerConfig producerConfig) {
        producerService.start(producerConfig);
    }

    @PostMapping("/stop")
    public void stop() {
        producerService.stop();
    }


}
