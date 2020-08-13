package io.github.mat3e.task;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
class TaskService {
    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    TaskDto save(TaskDto toSave) {
        return new TaskDto(
                taskRepository.save(
                        taskRepository.findById(toSave.getId())
                                .map(existingTask -> {
                                    if (existingTask.isDone() != toSave.isDone()) {
                                        existingTask.setChangesCount(existingTask.getChangesCount() + 1);
                                        existingTask.setDone(toSave.isDone());
                                    }
                                    existingTask.setAdditionalComment(toSave.getAdditionalComment());
                                    existingTask.setDeadline(toSave.getDeadline());
                                    existingTask.setDescription(toSave.getDescription());
                                    return existingTask;
                                }).orElseGet(() -> {
                            var result = new Task(toSave.getDescription(), toSave.getDeadline(), null);
                            result.setAdditionalComment(toSave.getAdditionalComment());
                            return result;
                        })
                )
        );
    }

    List<TaskDto> list() {
        return taskRepository.findAll().stream()
                .map(TaskDto::new)
                .collect(toList());
    }

    List<TaskWithChangesDto> listWithChanges() {
        return taskRepository.findAll().stream()
                .map(TaskWithChangesDto::new)
                .collect(toList());
    }

    Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(TaskDto::new);
    }

    void delete(int id) {
        taskRepository.deleteById(id);
    }
}
