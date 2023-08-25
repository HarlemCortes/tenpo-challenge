package com.harlem.tenpo.sumservice.gateway.percentage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

@Component
@Slf4j
public class PercentageServiceClientImpl implements PercentageServiceClient{

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    private static final int BOUND_PERCENTAGE = 101;

    public PercentageServiceClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }

    @Override
    public Optional<Integer> getPercentage() {
        log.info("Fetching percentage from service...");
        // String serviceUrl = "http://example.com/percentage";
        // Optional<Integer> percentage = restTemplate.getForObject(serviceUrl, Integer.class);

        // Simulate an intentional error for testing
        if (random.nextInt(10) < 8) { // Simulate an 80% chance of failure
            log.error("Simulated service error.");
            throw new RuntimeException("Simulated service error.");
        }

        int randomPercentage = generateRandomPercentage();
        log.info("Random percentage generated: {}", randomPercentage);
        return Optional.of(randomPercentage);
    }

    private int generateRandomPercentage() {
        return random.nextInt(BOUND_PERCENTAGE); // Generates a random integer between 0 and 100 (inclusive)
    }
}
