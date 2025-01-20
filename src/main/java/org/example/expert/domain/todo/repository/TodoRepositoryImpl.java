package org.example.expert.domain.todo.repository;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom{

  private final JPAQueryFactory queryFactory;

  public Todo getTodo(Long todoId) {
    Todo getTodo = queryFactory.selectFrom(todo)
        .leftJoin(todo.user)
        .fetchJoin()
        .where(todo.id.eq(todoId))
        .fetchOne();
    if(getTodo==null) {
      throw new InvalidRequestException("Todo not found");
    }
    return getTodo;
  }

//  SELECT t.id, t.title, COUNT(m.id) AS manager_count, COUNT(c.id) AS comment_count
//  FROM todos t
//  LEFT JOIN managers m ON t.id = m.todo_id
//  LEFT JOIN comments c ON t.id = c.todo_id
//  LEFT JOIN users u ON m.user_id = u.id
//  WHERE u.username LIKE '%he%'
//  AND t.created_at BETWEEN '2025-01-15 17:59:49.059000' AND '2025-01-17 09:39:52.203979'
//  AND t.title LIKE '%제목%'
//  GROUP BY t.id
//  ORDER BY t.created_at DESC;

  @Override
  public Page<TodoSearchResponse> getSearchResponse(Pageable pageable, String title,
      LocalDateTime starttime, LocalDateTime endtime, String managerName) {
    List<TodoSearchResponse> todos = queryFactory.select(
        Projections.constructor(
            TodoSearchResponse.class,
            todo,
            manager.id.countDistinct(),
            comment.id.countDistinct()
        )
    )
        .from(todo)
        .leftJoin(comment).on(comment.todo.id.eq(todo.id))
        .leftJoin(manager).on(manager.todo.id.eq(todo.id))
        .leftJoin(user).on(user.id.eq(manager.user.id))
        .where(
            likeTitle(title),
            todo.createdAt.between(starttime,endtime),
            likeManagerName(managerName)
        )
        .groupBy(todo.id)
        .orderBy(todo.createdAt.desc())
        .limit(pageable.getPageSize())
        .offset(pageable.getPageNumber())
        .fetch();

    Long totalCount = Optional.ofNullable(queryFactory.select(Wildcard.count)
        .from(todo)
        .leftJoin(comment).on(comment.todo.id.eq(todo.id))
        .leftJoin(manager).on(manager.todo.id.eq(todo.id))
        .leftJoin(user).on(user.id.eq(manager.user.id))
        .where(
            likeTitle(title),
            todo.createdAt.between(starttime,endtime),
            likeManagerName(managerName)
        ).fetchOne())
        .orElse(0L);
    return new PageImpl<>(todos,pageable,totalCount);
  }

  public BooleanExpression likeTitle(String title) {
    if(title == null) {
      return null;
    }
    return todo.title.contains(title);
  }

  public BooleanExpression likeManagerName(String managerName) {
    if(managerName == null) {
      return null;
    }
    return manager.user.username.contains(managerName);
  }



}


