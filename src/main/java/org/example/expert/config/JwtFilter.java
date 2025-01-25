package org.example.expert.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter implements Filter {

  private final JwtUtil jwtUtil;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String url = httpRequest.getRequestURI();

    if (url.startsWith("/auth")||url.startsWith("/healthz")) {
      chain.doFilter(request, response);
      return;
    }

    String bearerJwt = httpRequest.getHeader("Authorization");

    if (bearerJwt == null) {
      // 토큰이 없는 경우 400을 반환합니다.
      chain.doFilter(request, response);
//      httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
      return;
    }

    String jwt = jwtUtil.substringToken(bearerJwt);

    try {
      // JWT 유효성 검사와 claims 추출
      Claims claims = jwtUtil.extractClaims(jwt);
      if (claims == null) {
        httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
        return;
      }

      Long userId = Long.parseLong(claims.getSubject());
      String username = (String) claims.get("username");
      UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));
      String email = (String) claims.get("email");

      AuthUser user = new AuthUser(userId, email, username, userRole);

      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));


      chain.doFilter(request, response);
    } catch (SecurityException | MalformedJwtException e) {
      log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
      httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
      httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
    } catch (Exception e) {
      log.error("Internal server error", e);
      httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}
