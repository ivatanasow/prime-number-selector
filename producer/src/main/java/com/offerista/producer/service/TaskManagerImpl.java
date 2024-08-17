package com.offerista.producer.service;

import com.offerista.producer.exception.TaskAlreadyRunningException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskManagerImpl implements TaskManager {

    private final ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    public TaskManagerImpl(ThreadPoolTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    @Override
    public synchronized void startAtFixedRate(Runnable task, long duration) {
        if (isRunning()) {
            throw new TaskAlreadyRunningException();
        }
        scheduledFuture = taskScheduler.scheduleAtFixedRate(task, Duration.ofMillis(duration));
    }

    @Override
    public void stop() {
        scheduledFuture.cancel(true);
        this.scheduledFuture = null;
    }

    @Override
    public boolean isRunning() {
        return scheduledFuture != null && !scheduledFuture.isDone();
    }
}
