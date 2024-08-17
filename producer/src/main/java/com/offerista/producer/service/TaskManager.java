package com.offerista.producer.service;

public interface TaskManager {

    void startAtFixedRate(Runnable task, long duration);

    void stop();

    boolean isRunning();
}
