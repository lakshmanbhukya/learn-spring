package org.example.learnspring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* org.example.learnspring.service.*.*(..))")
    public void todoServiceMethods(){
    }

    @Around("todoServiceMethods()")
    public Object todoServiceLogsServieMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String arguments= Arrays.toString(joinPoint.getArgs());
        log.info("[Service Start] Executing method {} with arguments {}", methodName, arguments);
        long starttime=System.currentTimeMillis();
        Object result;
        try{
            result=joinPoint.proceed();
            log.info("[Service End] Executing method {} with arguments {}", methodName, arguments);
        } catch (Throwable e) {
            log.info("[Service End] Executing method {} with arguments {}", methodName, arguments);
            throw e;
        }finally {
            long executiontime=System.currentTimeMillis()-starttime;
            log.info("[Service End] Executing method {} with arguments {}", methodName, arguments);
        }
        return result;
    }

}
