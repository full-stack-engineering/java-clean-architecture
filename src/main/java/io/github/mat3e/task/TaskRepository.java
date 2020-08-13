package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface TaskRepository extends Repository<Task, Integer> {
    Optional<Task> findById(Integer id);

    <S extends Task> S save(S entity);

    <S extends Task> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}
