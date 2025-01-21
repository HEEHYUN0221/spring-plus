package org.example.expert.domain.common;

import org.example.expert.domain.common.entity.ManagerRegisterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogRepository extends JpaRepository<ManagerRegisterLog, Long> {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @Override
  <S extends ManagerRegisterLog> S save(S entity);
}
