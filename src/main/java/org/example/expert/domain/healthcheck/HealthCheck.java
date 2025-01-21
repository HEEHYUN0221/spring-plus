package org.example.expert.domain.healthcheck;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheck {

  @GetMapping("/healthz")
  public String healthz() {
    return "health-check";
  }

}
