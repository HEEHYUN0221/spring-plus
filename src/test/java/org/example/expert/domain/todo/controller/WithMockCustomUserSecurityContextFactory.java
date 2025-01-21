package org.example.expert.domain.todo.controller;

import java.util.Collections;
import java.util.List;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.controller.TodoControllerTest.WithMockCustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
    WithSecurityContextFactory<WithMockCustomUser> {
  @Override
  public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    AuthUser authUser = new AuthUser(annotation.id(), annotation.email(), annotation.username(), annotation.role());
    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + annotation.role().name()));
    Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null, authorities);
    context.setAuthentication(authentication);
    return context;
  }
}
