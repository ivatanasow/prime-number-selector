package com.offerista.producer.exception;

public class TaskAlreadyRunningException extends RuntimeException {

    public TaskAlreadyRunningException() {
    }

    public TaskAlreadyRunningException(String message) {
        super(message);
    }
}
