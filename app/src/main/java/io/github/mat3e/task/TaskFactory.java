package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;

class TaskFactory {
    Task from(final TaskDto source) {
        return Task.restore(new TaskSnapshot(
                source.getId(),
                source.getDescription(),
                source.isDone(),
                source.getDeadline(),
                0,
                source.getAdditionalComment(),
                null
        ));
    }
}
