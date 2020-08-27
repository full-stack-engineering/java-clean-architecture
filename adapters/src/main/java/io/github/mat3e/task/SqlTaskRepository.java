package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

interface SqlTaskRepository extends Repository<TaskSnapshot, Integer> {
    Optional<TaskSnapshot> findById(Integer id);

    <S extends TaskSnapshot> S save(S entity);

    <S extends TaskSnapshot> List<S> saveAll(Iterable<S> entities);

    void deleteById(Integer id);
}

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<TaskSnapshot, Integer> {
}

@org.springframework.stereotype.Repository
class TaskRepositoryImpl implements TaskRepository {
    private final SqlTaskRepository repository;

    TaskRepositoryImpl(final SqlTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Task> findById(final Integer id) {
        return repository.findById(id).map(Task::restore);
    }

    @Override
    public Task save(final Task entity) {
        return Task.restore(repository.save(entity.getSnapshot()));
    }

    @Override
    public List<Task> saveAll(final Iterable<Task> entities) {
        return repository.saveAll(
                StreamSupport.stream(entities.spliterator(), false)
                        .map(Task::getSnapshot)
                        .collect(toList())
        ).stream()
                .map(Task::restore)
                .collect(toList());
    }

    @Override
    public void deleteById(final Integer id) {
        repository.deleteById(id);
    }
}
