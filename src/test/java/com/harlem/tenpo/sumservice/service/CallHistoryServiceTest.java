package com.harlem.tenpo.sumservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.harlem.tenpo.sumservice.dto.CallHistoryResponse;
import com.harlem.tenpo.sumservice.dto.CallHistoryDTO;
import com.harlem.tenpo.sumservice.model.CallHistory;
import com.harlem.tenpo.sumservice.repository.CallHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CallHistoryServiceTest {

    private CallHistoryService callHistoryService;

    @Mock
    private CallHistoryRepository callHistoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        callHistoryService = new CallHistoryService(callHistoryRepository);
    }

    @Test
    public void testSaveCallHistory() {
        CallHistoryDTO mockCallHistoryDTO = new CallHistoryDTO();
        mockCallHistoryDTO.setMethod("GET");
        mockCallHistoryDTO.setEndpoint("/api/endpoint");
        mockCallHistoryDTO.setStatus("OK");
        mockCallHistoryDTO.setResponse("Response");

        callHistoryService.saveCallHistory(mockCallHistoryDTO);
        verify(callHistoryRepository, times(1)).save(any(CallHistory.class));
    }

    @Test
    public void testGetCallHistory() {
        int page = 0;
        int size = 10;
        String sortOrder = "asc";

        List<CallHistory> mockCallHistoryList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CallHistory history = new CallHistory();
            history.setId((long) i);
            history.setTimestamp(LocalDateTime.now());
            history.setMethod("GET");
            history.setEndpoint("/api/endpoint" + i);
            history.setStatus("OK");
            history.setResponse("Response " + i);
            mockCallHistoryList.add(history);
        }

        Page<CallHistory> mockPage = new PageImpl<>(mockCallHistoryList);
        when(callHistoryRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<CallHistoryResponse> result = callHistoryService.getCallHistory(page, size, sortOrder);
        assertEquals(mockPage.map(callHistoryService::mapToCallHistoryResponse), result);
    }

}
