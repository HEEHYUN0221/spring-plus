package org.example.expert.domain.todo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

  private final TodoRepository todoRepository;
  private final WeatherClient weatherClient;

  public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
    User user = User.fromAuthUser(authUser);

    String weather = weatherClient.getTodayWeather();

    Todo newTodo = new Todo(todoSaveRequest.getTitle(), todoSaveRequest.getContents(), weather,
        user);
    Todo savedTodo = todoRepository.save(newTodo);

    return new TodoSaveResponse(savedTodo.getId(), savedTodo.getTitle(), savedTodo.getContents(),
        weather, new UserResponse(user.getId(), user.getEmail(), user.getUsername()));
  }

  public Page<TodoResponse> getTodos(int page, int size, String weather, LocalDate start,
      LocalDate end) {
    Pageable pageable = PageRequest.of(page - 1, size);

    LocalDateTime starttime = LocalDateTime.of(1980, 1, 1, 0, 0, 0);
    LocalDateTime endtime = LocalDateTime.now();

    if (!(start == null)) {
      starttime = start.atStartOfDay();
    }

    if (!(end == null)) {
      endtime = end.atTime(23, 59, 59);
    }

    Page<Todo> todos = todoRepository.findAllByWeatherAndModifiedAtOrderByModifiedAtDesc(pageable,
        weather, starttime, endtime);

    return todos.map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.getContents(),
        todo.getWeather(), new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(),todo.getUser().getUsername()),
        todo.getCreatedAt(), todo.getModifiedAt()));
  }

  public TodoResponse getTodo(long todoId) {
    Todo todo = todoRepository.getTodo(todoId);

    User user = todo.getUser();

    return new TodoResponse(todo.getId(), todo.getTitle(), todo.getContents(), todo.getWeather(),
        new UserResponse(user.getId(), user.getEmail(), user.getUsername()), todo.getCreatedAt(), todo.getModifiedAt());
  }

  public Page<TodoSearchResponse> getSearchTodo(int page, int size, String title, LocalDate start, LocalDate end, String managerName) {

    Pageable pageable = PageRequest.of(page - 1, size);

    LocalDateTime starttime = LocalDateTime.of(1980, 1, 1, 0, 0, 0);
    LocalDateTime endtime = LocalDateTime.now();

    if (!(start == null)) {
      starttime = start.atStartOfDay();
    }

    if (!(end == null)) {
      endtime = end.atTime(23, 59, 59);
    }

    Page<TodoSearchResponse> todoSearchList = todoRepository.getSearchResponse(pageable, title, starttime, endtime, managerName);

    return todoSearchList;
  }
}
