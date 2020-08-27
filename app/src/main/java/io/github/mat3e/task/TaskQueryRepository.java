package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;

import java.util.Optional;
import java.util.Set;

public interface TaskQueryRepository {
    int count();

    Optional<TaskDto> findDtoById(Integer id);

    boolean existsByDoneIsFalseAndProject_Id(int id);

    <T> Set<T> findBy(Class<T> type);
}
