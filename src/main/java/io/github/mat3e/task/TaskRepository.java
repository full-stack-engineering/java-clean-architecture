package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends Repository<Task, Integer> {
    int count();

    Optional<Task> findById(Integer id);

    List<Task> findAllByProject_Id(int id);

    List<Task> findAll();

    <S extends Task> S save(S entity);

    <S extends Task> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}
