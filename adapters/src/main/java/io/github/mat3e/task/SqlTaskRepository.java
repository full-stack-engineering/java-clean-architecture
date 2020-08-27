package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

interface SqlTaskRepository extends TaskRepository, Repository<Task, Integer> {
}
