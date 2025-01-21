//package org.example.expert.aop;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Objects;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.example.expert.domain.common.LogRepository;
//import org.example.expert.domain.common.dto.AuthUser;
//import org.example.expert.domain.common.entity.ManagerRegisterLog;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//
//@Slf4j
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class ManagerRegisterLoggingAspect {
//
//  private final LogRepository logRepository;
//
//  @Around("execution(* org.example.expert.domain.manager.controller.ManagerController.saveManager(..))")
//  public Object managerRegister(ProceedingJoinPoint joinPoint) throws Throwable {
//    boolean isSuccess = false;
//
//    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
//    ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
//
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    AuthUser authUser = (AuthUser) auth.getPrincipal();
//    Long managerUserId = getRequestBody(requestWrapper);
//
//    try {
//      Object result = joinPoint.proceed();
//      isSuccess = true;
//      return result;
//    } catch (Throwable e) {
//      throw e;
//    } finally {
//      ManagerRegisterLog log = new ManagerRegisterLog(authUser.getId(), managerUserId);
//      log.setSuccess(isSuccess);
//      logRepository.save(log);
//    }
//  }
//
//  public void savelog(ManagerRegisterLog log) {
//    logRepository.save(log);
//  }
//
////  @After("execution(* org.example.expert.domain.manager.controller.ManagerController.saveManager(..))")
////  public void managerRegister(JoinPoint joinPoint) throws Throwable{
////    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////    HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
////    ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
////
////    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////    AuthUser authUser = (AuthUser) auth.getPrincipal();
////    Long managerUserId = getRequestBody(requestWrapper);
////    ManagerRegisterLog log = new ManagerRegisterLog(authUser.getId(),managerUserId);
////    log.setSuccess(setSuccess);
////    logRepository.save(log);
////    setSuccess = true;
////  }
////
////  @AfterThrowing("execution(* org.example.expert.domain.manager.controller.ManagerController.saveManager(..))")
////  public void managerRegisterAfter() {
////    setSuccess = false;
////  }
//
//
//  private Long getRequestBody(ContentCachingRequestWrapper wrapper) throws IOException {
//    byte[] contentAsByteArray = wrapper.getContentAsByteArray();
//    String requestBody = new String(contentAsByteArray, wrapper.getCharacterEncoding());
//    ObjectMapper objectMapper = new ObjectMapper();
//    JsonNode jsonNode = objectMapper.readTree(requestBody);
//
//    return jsonNode.get("managerUserId").asLong();
//  }
//}
