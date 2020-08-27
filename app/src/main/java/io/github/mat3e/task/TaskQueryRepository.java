package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesQueryDto;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskQueryRepository extends Repository<Task, Integer> {
    int count();

    Optional<TaskDto> findDtoById(Integer id);

    boolean existsByDoneIsFalseAndProject_Id(int id);

    List<TaskWithChangesQueryDto> findAllWithChangesBy();

    List<TaskDto> findAllBy();

    <T> Set<T> findBy(Class<T> type);
}
