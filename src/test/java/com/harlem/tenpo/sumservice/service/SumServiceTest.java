package com.harlem.tenpo.sumservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.harlem.tenpo.sumservice.dto.CallHistoryResponse;
import com.harlem.tenpo.sumservice.dto.SumResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SumServiceTest {

    private SumService sumService;

    @Mock
    private CallHistoryService callHistoryService;

    @Mock
    private PercentageService percentageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sumService = new SumService(callHistoryService, percentageService);
    }

    @Test
    public void testSumNumbers() {
        int mockPercentage = 10;
        when(percentageService.generateRandomPercentage()).thenReturn(mockPercentage);

        SumResponse result = sumService.sumNumbers(5.0, 7.0);

        double expectedSum = 5.0 + 7.0;
        double expectedTotal = expectedSum * (1 + (mockPercentage / 100));

        assertEquals(5.0, result.getFirstNumber());
        assertEquals(7.0, result.getSecondNumber());
        assertEquals(mockPercentage, result.getPercentage());
        assertEquals(expectedTotal, result.getTotal());
    }

    @Test
    public void testGetCallHistory() {
        int page = 0;
        int size = 10;
        String sort = "timestamp";

        List<CallHistoryResponse> mockCallHistoryList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CallHistoryResponse history = CallHistoryResponse.builder()
                    .id((long) i)
                    .timestamp(LocalDateTime.now())
                    .method("GET")
                    .endpoint("/api/endpoint" + i)
                    .status("OK")
                    .response("Response " + i)
                    .build();
            mockCallHistoryList.add(history);
        }

        Page<CallHistoryResponse> mockPage = new PageImpl<>(mockCallHistoryList);
        when(callHistoryService.getCallHistory(page, size, sort)).thenReturn(mockPage);

        Page<CallHistoryResponse> result = sumService.getCallHistory(page, size, sort);
        assertEquals(mockPage, result);
    }
}

