package com.harlem.tenpo.sumservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.harlem.tenpo.sumservice.gateway.percentage.PercentageServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PercentageServiceTest {

    private PercentageService percentageService;

    @Mock
    private PercentageServiceClient percentageServiceClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        percentageService = new PercentageService(percentageServiceClient);
    }

    @Test
    public void testGenerateRandomPercentage_Success() {
        int mockPercentage = 10;
        when(percentageServiceClient.getPercentage()).thenReturn(Optional.of(mockPercentage));

        int result = percentageService.generateRandomPercentage();
        assertEquals(mockPercentage, result);
        verify(percentageServiceClient, times(1)).getPercentage();
    }

    @Test
    public void testGenerateRandomPercentage_Failure() {
        when(percentageServiceClient.getPercentage()).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> percentageService.generateRandomPercentage());
        verify(percentageServiceClient, times(1)).getPercentage();
    }
}

