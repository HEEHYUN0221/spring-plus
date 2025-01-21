package org.example.expert.domain.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "log")
@Getter
@Entity
@NoArgsConstructor
public class ManagerRegisterLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime logWriteTime;

  @Setter
  private Boolean isSuccess;

  private Long requestUserId;

  private Long managerUserId;

  private String exceptionMessage;


  public ManagerRegisterLog(Long requestUserId, Long managerUserId) {
    this.logWriteTime = LocalDateTime.now();
    this.requestUserId = requestUserId;
    this.managerUserId = managerUserId;
    this.isSuccess = true;
  }

  public void setExceptionMessage(RuntimeException e) {
    this.exceptionMessage = e.getMessage();
  }

}
