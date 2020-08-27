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
            taskRepository.save(Task.restore(
                    new TaskSnapshot(0, "Example task", false, ZonedDateTime.now(), 0, null, null)
            ));
        }
    }
}
