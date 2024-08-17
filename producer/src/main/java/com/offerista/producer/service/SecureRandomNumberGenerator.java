package com.offerista.producer.service;

import com.offerista.producer.model.ProducerConfig;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class SecureRandomNumberGenerator implements NumberGenerator {

    private final SecureRandom rng;

    private final int streamSize;

    private final int lowerBound;

    private final int upperBound;

    private SecureRandomNumberGenerator(int streamSize, int lowerBound, int upperBound) {
        this.streamSize = streamSize;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        try {
            this.rng = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static SecureRandomNumberGenerator getInstance(ProducerConfig config) {
        return new SecureRandomNumberGenerator(config.getChunkSize(), config.getLowerBound(), config.getUpperBound());
    }

    @Override
    public List<Integer> generateNumbers() {
        return rng.ints(streamSize, lowerBound, upperBound)
                .boxed()
                .collect(Collectors.toList());
    }
}
