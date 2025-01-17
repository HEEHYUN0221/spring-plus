package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;

public interface TodoRepositoryCustom {
  Todo getTodo(Long todoId);
}
