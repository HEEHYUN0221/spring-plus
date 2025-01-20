package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {
  Todo getTodo(Long todoId);

  Page<TodoSearchResponse> getSearchResponse(Pageable pageable, String title, LocalDateTime starttime, LocalDateTime endtime, String managerName);
}
