package com.offerista.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String PRIME_NUMBERS_TOPIC = "prime-numbers";
    public static final String RANDOM_NUMBERS_TOPIC = "random-numbers";

    @Bean
    NewTopic primeNumbers() {
        return TopicBuilder.name(PRIME_NUMBERS_TOPIC)
                .partitions(1)
                .build();
    }
}
