package com.harlem.tenpo.sumservice.service;

import com.harlem.tenpo.sumservice.dto.CallHistoryDTO;
import com.harlem.tenpo.sumservice.dto.CallHistoryResponse;
import com.harlem.tenpo.sumservice.model.CallHistory;
import com.harlem.tenpo.sumservice.repository.CallHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CallHistoryService {

    private final CallHistoryRepository callHistoryRepository;

    @Async
    public void saveCallHistory(CallHistoryDTO callHistoryDTO) {
        try {
            log.info("Saving call history...");
            CallHistory callHistory = new CallHistory();
            callHistory.setTimestamp(LocalDateTime.now());
            callHistory.setMethod(callHistoryDTO.getMethod());
            callHistory.setEndpoint(callHistoryDTO.getEndpoint());
            callHistory.setStatus(callHistoryDTO.getStatus());
            callHistory.setResponse(callHistoryDTO.getResponse());

            callHistoryRepository.save(callHistory);
            log.info("Call history saved successfully.");
        } catch (Exception e) {
            log.error("Error while saving call history: {}", e.getMessage());
        }
    }

    public Page<CallHistoryResponse> getCallHistory(int page, int size, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(new Sort.Order(direction, "timestamp"));

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CallHistory> callHistoryPage = callHistoryRepository.findAll(pageable);
        log.info("Call history retrieved successfully.");

        return callHistoryPage.map(this::mapToCallHistoryResponse);
    }

    public CallHistoryResponse mapToCallHistoryResponse(CallHistory callHistory) {
        return CallHistoryResponse.builder()
                .id(callHistory.getId())
                .timestamp(callHistory.getTimestamp())
                .method(callHistory.getMethod())
                .endpoint(callHistory.getEndpoint())
                .status(callHistory.getStatus())
                .response(callHistory.getResponse())
                .build();
    }
}
