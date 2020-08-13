package io.github.mat3e.task;

import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.dto.TaskWithChangesQueryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
class TaskController {
    private final TaskFacade taskFacade;
    private final TaskQueryRepository taskQueryRepository;

    TaskController(final TaskFacade taskFacade, final TaskQueryRepository taskQueryRepository) {
        this.taskFacade = taskFacade;
        this.taskQueryRepository = taskQueryRepository;
    }

    @GetMapping
    List<TaskDto> list() {
        return taskQueryRepository.findAllBy();
    }

    @GetMapping(params = "changes")
    List<TaskWithChangesQueryDto> listWithChanges() {
        return taskQueryRepository.findAllWithChangesBy();
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> get(@PathVariable int id) {
        return taskQueryRepository.findDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<TaskDto> update(@PathVariable int id, @RequestBody TaskDto toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        taskFacade.save(toUpdate.withId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<TaskDto> create(@RequestBody TaskDto toCreate) {
        TaskDto result = taskFacade.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TaskDto> delete(@PathVariable int id) {
        taskFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
