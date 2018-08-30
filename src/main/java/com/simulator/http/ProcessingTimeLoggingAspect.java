package com.simulator.http;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ProcessingTimeLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ProcessingTimeLoggingAspect.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object returnObject;
        try {
            returnObject = pjp.proceed();
        } finally {
            stopWatch.stop();
            logger.info("{} : {} ms", pjp.getSignature(), stopWatch.getTotalTimeMillis());
        }
        return returnObject;
    }

}