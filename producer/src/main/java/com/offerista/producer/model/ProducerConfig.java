package com.offerista.producer.model;


import jakarta.validation.constraints.Min;

public class ProducerConfig {

    @Min(value = 1, message = "Message sending rate cannot be less than 1ms")
    private long rate;

    @Min(value = 1, message = "Message chunk size cannot be less than 1")
    private int chunkSize;

    @Min(value = 1, message = "Lower bound cannot be less than 1")
    private int lowerBound;

    private int upperBound;

    public ProducerConfig() {
    }

    public long getRate() {
        return rate;
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
