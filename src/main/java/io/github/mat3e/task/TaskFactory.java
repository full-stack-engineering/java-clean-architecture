package io.github.mat3e.task;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import org.springframework.stereotype.Service;

@Service
class TaskFactory {
    Task from(final TaskDto source, final SimpleProjectQueryDto project) {
        var result = new Task(source.getDescription(), source.getDeadline(), project);
        result.setId(source.getId());
        result.setAdditionalComment(source.getAdditionalComment());
        result.setDone(source.isDone());
        return result;
    }
}
