package io.github.mat3e.task;

import java.time.ZonedDateTime;

class TaskInitializer {
    private final TaskRepository taskRepository;
    private final TaskQueryRepository taskQueryRepository;

    TaskInitializer(final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.taskRepository = taskRepository;
        this.taskQueryRepository = taskQueryRepository;
    }

    void init() {
        if (taskQueryRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
    }
}
