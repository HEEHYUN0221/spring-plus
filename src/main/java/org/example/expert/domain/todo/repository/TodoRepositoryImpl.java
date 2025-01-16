package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.todo.entity.QTodo.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  public Todo getTodo(Long todoId) {
    Todo getTodo = queryFactory.selectFrom(todo).where(todo.id.eq(todoId)).fetchOne();
    if(getTodo==null) {
      throw new InvalidRequestException("Todo not found");
    }
    return getTodo;
  }

}
