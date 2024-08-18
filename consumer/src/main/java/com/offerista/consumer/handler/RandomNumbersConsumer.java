package com.offerista.consumer.handler;

import com.offerista.consumer.config.KafkaConfig;
import com.offerista.consumer.service.PrimeNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomNumbersConsumer {

    private static final Logger log = LoggerFactory.getLogger(RandomNumbersConsumer.class);

    private final PrimeNumberService primeNumberService;

    private final KafkaTemplate<String, List<Integer>> kafkaTemplate;

    public RandomNumbersConsumer(PrimeNumberService primeNumberService, KafkaTemplate<String, List<Integer>> kafkaTemplate) {
        this.primeNumberService = primeNumberService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaConfig.RANDOM_NUMBERS_TOPIC, groupId = "rng-consumer")
    private void consume(List<Integer> numbers) {
        log.debug("Numbers consumed [{}]", numbers);
        List<Integer> primeNumbers = primeNumberService.extractPrimeNumbers(numbers);

        kafkaTemplate.send(KafkaConfig.PRIME_NUMBERS_TOPIC, primeNumbers);
    }
}
