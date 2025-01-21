package org.example.expert.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  private final JwtFilter jwtFilter;  //필터를 bean으로 등록해줘야 오류가 나지 않으니 명심할 것

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)  // .csrf().disable() 방식은 더 이상 사용 안함.
        .httpBasic(AbstractHttpConfigurer::disable) // BasicAuthenticationFilter 비활성화
        .formLogin(AbstractHttpConfigurer::disable) // UsernamePasswordAuthenticationFilter, DefaultLoginPageGeneratingFilter 비활성화
        .addFilterBefore(jwtFilter, SecurityContextHolderAwareRequestFilter.class) // jwtFilter > SecurityContextHolderAwareRequestFilter 순서로 동작
        .authorizeHttpRequests(auth -> auth //역할별 허용 URL 작성하는 부분
            .requestMatchers("/healthz").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers("/admin/users/**").hasRole("ADMIN")
            .requestMatchers("/**").hasRole("USER")
            .anyRequest().authenticated()
        )
        .build();
  }
}
