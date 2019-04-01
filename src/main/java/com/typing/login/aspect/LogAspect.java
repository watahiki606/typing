package com.typing.login.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around("execution(* *..*.*Controller.*(..))")
    public Object startLog(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("[method start]:" + jp.getSignature());
        try {
            Object result = jp.proceed();
            System.out.println("[method end]:" + jp.getSignature());
            return result;
        } catch (Exception e) {
            System.out.println("[method err]:" + jp.getSignature());
            e.printStackTrace();
            throw e;
        }
    }

}