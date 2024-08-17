package com.offerista.consumer.handler;

import com.offerista.consumer.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrimeNumberConsumer {

    @KafkaListener(topics = KafkaConfig.PRIME_NUMBERS_TOPIC, groupId = "prime-numbers")
    private void consume(List<Integer> numbers) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("prime_numbers.csv", true)))) {
            String toWriteInCsvFile = numbers.stream().map(String::valueOf).collect(Collectors.joining(", "));
            printWriter.println(toWriteInCsvFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
