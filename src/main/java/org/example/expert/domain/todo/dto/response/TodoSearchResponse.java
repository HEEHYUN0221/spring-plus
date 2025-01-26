package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import org.example.expert.domain.todo.entity.Todo;

@Getter
public class TodoSearchResponse {
  private final Long todoId;
  private final String title;
  private final int managerCount;
  private final int commentCount;

  public TodoSearchResponse(Todo todo, int managerCount, int commentCount) {
    this.todoId = todo.getId();
    this.title = todo.getTitle();
    this.managerCount = managerCount;
    this.commentCount =  commentCount;
  }
}
