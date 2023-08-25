package com.harlem.tenpo.sumservice.service;

import com.harlem.tenpo.sumservice.dto.CallHistoryResponse;
import com.harlem.tenpo.sumservice.dto.SumResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class SumService {

    private final CallHistoryService callHistoryService;
    private final PercentageService percentageService;

    public SumResponse sumNumbers(double firstNumber, double secondNumber) {
        log.info("Calculating sum of {} and {}", firstNumber, secondNumber);
        int percentage = percentageService.generateRandomPercentage();
        double sum = firstNumber + secondNumber;
        double total = sum * (1 + (percentage / 100));

        SumResponse sumResponse = SumResponse.builder()
                .firstNumber(firstNumber)
                .secondNumber(secondNumber)
                .percentage(percentage)
                .total(total).build();

        return sumResponse;
    }

    public Page<CallHistoryResponse> getCallHistory(int page, int size, String sort) {
        log.info("Getting call history - Page: {}, Size: {}, Sort: {}", page, size, sort);
        return callHistoryService.getCallHistory(page, size, sort);
    }
}
