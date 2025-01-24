package org.example.expert.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class TimeCheckAOP {

  @Around("execution(* org.example.expert.domain.user.controller.UserController.getAllUser(..))")
  public Object TimeCheckAOPmethod(ProceedingJoinPoint joinPoint) throws Throwable {
    long beforeTime = System.currentTimeMillis();

    Object result = joinPoint.proceed();

    long afterTime = System.currentTimeMillis();
    log.info("조회에 걸린 시간 : " + (afterTime-beforeTime) + "ms");

    return result;
  }
}
