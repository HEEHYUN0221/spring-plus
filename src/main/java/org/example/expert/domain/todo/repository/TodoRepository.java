package org.example.expert.domain.todo.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long>,TodoRepositoryCustom{


  @Query("SELECT t FROM Todo t LEFT JOIN t.user u " +
      "WHERE (:weather IS NULL OR t.weather = :weather) " +
      "AND (:starttime IS NULL OR t.modifiedAt >= :starttime) " +
      "AND (:endtime IS NULL OR t.modifiedAt <= :endtime) " +
      "ORDER BY t.modifiedAt DESC")
  Page<Todo> findAllByWeatherAndModifiedAtOrderByModifiedAtDesc(Pageable pageable, @Param("weather") String weather,
      @Param("starttime") LocalDateTime starttime, @Param("endtime") LocalDateTime endtime);


}
