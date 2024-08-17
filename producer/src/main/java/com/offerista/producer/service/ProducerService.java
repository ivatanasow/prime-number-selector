package com.offerista.producer.service;

import com.offerista.producer.config.KafkaConfig;
import com.offerista.producer.exception.TaskAlreadyRunningException;
import com.offerista.producer.model.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

    private final TaskManager taskManager;

    private final KafkaTemplate<String, List<Integer>> kafkaTemplate;

    private final Consumer<Throwable> errorHandler;

    public ProducerService(TaskManager taskManager, KafkaTemplate<String, List<Integer>> kafkaTemplate) {
        this.taskManager = taskManager;
        this.kafkaTemplate = kafkaTemplate;

        this.errorHandler = (ex) -> taskManager.stop();
    }

    public synchronized void start(ProducerConfig config) {
        if (taskManager.isRunning()) {
            throw new TaskAlreadyRunningException();
        }

        log.info("Producer started");
        Runnable task = new ProducerTask(config, errorHandler, kafkaTemplate);
        taskManager.startAtFixedRate(task, config.getRate());
    }

    public void stop() {
        log.info("Producer stopped");
        taskManager.stop();
    }

    private static class ProducerTask implements Runnable {
        private final Consumer<Throwable> errorHandler;
        private final KafkaTemplate<String, List<Integer>> kafkaTemplate;
        private final NumberGenerator numberGenerator;

        public ProducerTask(ProducerConfig producerConfig, Consumer<Throwable> errorHandler, KafkaTemplate<String, List<Integer>> kafkaTemplate) {
            this.errorHandler = errorHandler;
            this.kafkaTemplate = kafkaTemplate;
            this.numberGenerator = SecureRandomNumberGenerator.getInstance(producerConfig);
        }

        @Override
        public void run() {
            List<Integer> randomNumbers = numberGenerator.generateNumbers();

            kafkaTemplate.send(KafkaConfig.RANDOM_NUMBERS_TOPIC, randomNumbers);
            log.debug("Numbers sent [{}]", randomNumbers);

            CompletableFuture.runAsync(() -> writeToFile(randomNumbers))
                    .exceptionally(ex -> {
                        log.error("I/O error occurred", ex);
                        errorHandler.accept(ex);
                        return null;
                    });
        }

        private void writeToFile(List<Integer> numbers) {
            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("random_numbers.csv", true)))) {
                String toWriteInCsvFile = numbers.stream().map(String::valueOf).collect(Collectors.joining(", "));
                printWriter.println(toWriteInCsvFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
