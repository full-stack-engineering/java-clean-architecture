package io.github.mat3e.task;

import java.util.List;
import java.util.Optional;

interface TaskRepository {
    Optional<Task> findById(Integer id);

    Task save(Task entity);

    List<Task> saveAll(Iterable<Task> entities);

    void deleteById(Integer id);
}
