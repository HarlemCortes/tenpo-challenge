package com.harlem.tenpo.sumservice.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Tamaño inicial del pool
        executor.setMaxPoolSize(25);  // Tamaño máximo del pool
        executor.setQueueCapacity(100); // Capacidad de la cola de tareas pendientes
        executor.setThreadNamePrefix("AsyncExecutor-"); // Prefijo de los nombres de los hilos
        executor.initialize();
        return executor;
    }
}
