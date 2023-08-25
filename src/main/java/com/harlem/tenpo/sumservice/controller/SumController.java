package com.harlem.tenpo.sumservice.controller;

import com.harlem.tenpo.sumservice.dto.CallHistoryResponse;
import com.harlem.tenpo.sumservice.dto.SumResponse;
import com.harlem.tenpo.sumservice.service.SumService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenpo/sum")
@RequiredArgsConstructor
@Slf4j
public class SumController {

    private final SumService sumService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @RateLimiter(name = "percentage")
    public SumResponse sumNumbers(@RequestParam(required = true) double firstNumber,
                                  @RequestParam(required = true) double secondNumber) {

        return sumService.sumNumbers(firstNumber, secondNumber);
    }

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<CallHistoryResponse> getAllByPage(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "asc") String sort) {

        return sumService.getCallHistory(page, size, sort);
    }
}
