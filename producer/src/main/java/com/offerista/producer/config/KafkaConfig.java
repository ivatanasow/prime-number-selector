package com.offerista.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String RANDOM_NUMBERS_TOPIC = "random-numbers";

    @Bean
    NewTopic randomNumbers() {
        return TopicBuilder.name(RANDOM_NUMBERS_TOPIC)
                .partitions(1)
                .build();
    }


}
