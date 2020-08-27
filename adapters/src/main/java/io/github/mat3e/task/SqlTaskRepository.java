package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

interface SqlTaskRepository extends Repository<SqlTask, Integer> {
    Optional<SqlTask> findById(Integer id);

    <S extends SqlTask> S save(S entity);

    <S extends SqlTask> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}

@org.springframework.stereotype.Repository
class TaskRepositoryImpl implements TaskRepository {
    private final SqlTaskRepository repository;

    TaskRepositoryImpl(final SqlTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return repository.findById(id).map(SqlTask::toTask);
    }

    @Override
    public Task save(final Task entity) {
        return repository.save(SqlTask.fromTask(entity)).toTask();
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return repository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(SqlTask::fromTask)
                        .collect(toList())
        ).stream()
                .map(SqlTask::toTask)
                .collect(toList());
    }

    @Override
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }
}
