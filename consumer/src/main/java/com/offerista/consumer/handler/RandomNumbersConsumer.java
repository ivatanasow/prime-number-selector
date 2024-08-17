package com.offerista.consumer.handler;

import com.offerista.consumer.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomNumbersConsumer {

    private static final Logger log = LoggerFactory.getLogger(RandomNumbersConsumer.class);

    private final KafkaTemplate<String, List<Integer>> kafkaTemplate;

    public RandomNumbersConsumer(KafkaTemplate<String, List<Integer>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaConfig.RANDOM_NUMBERS_TOPIC, groupId = "rng-consumer")
    private void consume(List<Integer> numbers) {
        log.debug("Numbers consumed [{}]", numbers);
        List<Integer> primeNumbers = numbers.stream().filter(this::isPrime).toList();

        kafkaTemplate.send(KafkaConfig.PRIME_NUMBERS_TOPIC, primeNumbers);
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        int sqrt = (int) Math.sqrt(number);
        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
