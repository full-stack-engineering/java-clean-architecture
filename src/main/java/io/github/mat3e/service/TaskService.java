package io.github.mat3e.service;

import io.github.mat3e.dto.TaskDto;
import io.github.mat3e.dto.TaskWithChangesDto;
import io.github.mat3e.entity.Task;
import io.github.mat3e.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDto save(TaskDto toSave) {
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

    public List<TaskDto> list() {
        return taskRepository.findAll().stream()
                .map(TaskDto::new)
                .collect(toList());
    }

    public List<TaskWithChangesDto> listWithChanges() {
        return taskRepository.findAll().stream()
                .map(TaskWithChangesDto::new)
                .collect(toList());
    }

    public Optional<TaskDto> get(int id) {
        return taskRepository.findById(id).map(TaskDto::new);
    }

    public void delete(int id) {
        taskRepository.deleteById(id);
    }
}
