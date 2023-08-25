package com.harlem.tenpo.sumservice.config.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncCallHistoryTaskExecutor {
    @Async
    public void execute(Runnable task) {
        task.run();
    }
}