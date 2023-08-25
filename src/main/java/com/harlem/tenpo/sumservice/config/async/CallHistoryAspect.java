package com.harlem.tenpo.sumservice.config.async;

import com.harlem.tenpo.sumservice.dto.CallHistoryDTO;
import com.harlem.tenpo.sumservice.service.CallHistoryService;
import com.harlem.tenpo.sumservice.util.CustomPageImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class CallHistoryAspect {

    @Autowired
    private CallHistoryService callHistoryService;

    @Autowired
    private AsyncCallHistoryTaskExecutor asyncTaskExecutor;

    @Pointcut("execution(* com.harlem.tenpo.sumservice.controller.SumController.*(..))")
    public void controllerMethod() {}

    @Around("controllerMethod()")
    public Object logCall(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        // Execute the controller method and capture the result
        Object result = null;
        String status;
        String resultString;

        try {
            result = joinPoint.proceed();
            status = "OK";
            if (result instanceof PageImpl<?>) {
                PageImpl<?> pageResult = (PageImpl<?>) result;
                CustomPageImpl<?> customPageResult = new CustomPageImpl<>(pageResult.getContent(), pageResult.getPageable(), pageResult.getTotalElements());
                resultString = customPageResult.toString();
            } else {
                resultString = result.toString();
            }
            saveCallHistory(method, endpoint, status, resultString);
        } catch (Exception e) {
            // If the method invocation resulted in an exception
           status = "ERROR";
           resultString = "";
           saveCallHistory(method, endpoint, status, resultString);
           throw e;
        }

        return result;
    }

    private void saveCallHistory(String method, String endpoint, String status, String response) {
        asyncTaskExecutor.execute(() -> {
            CallHistoryDTO callHistory = new CallHistoryDTO(method, endpoint, status, response);
            callHistoryService.saveCallHistory(callHistory);
        });
    }

}
