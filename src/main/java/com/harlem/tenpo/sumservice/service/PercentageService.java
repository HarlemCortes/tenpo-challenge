package com.harlem.tenpo.sumservice.service;

import com.harlem.tenpo.sumservice.exceptions.MissingPercentageException;
import com.harlem.tenpo.sumservice.gateway.percentage.PercentageServiceClient;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PercentageService {

    private final PercentageServiceClient percentageServiceClient;

    public PercentageService(PercentageServiceClient percentageServiceClient) {
        this.percentageServiceClient = percentageServiceClient;
    }

    @Cacheable(value = "percentageCache")
    @Retry(name = "percentage", fallbackMethod = "errorMethodFallback")
    public int generateRandomPercentage() {
        log.info("Generating random percentage...");
        return percentageServiceClient.getPercentage().orElseThrow(() -> new RuntimeException("ERROR: not value for percentage"));
    }

    public int errorMethodFallback(Exception e) {
        log.error("All retry attempts failed. No cached value available.");
        throw new MissingPercentageException("ERROR: fetching percentage value.");
    }

}
