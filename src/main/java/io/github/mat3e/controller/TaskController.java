package io.github.mat3e.controller;

import io.github.mat3e.dto.TaskDto;
import io.github.mat3e.dto.TaskWithChangesDto;
import io.github.mat3e.service.TaskService;
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
    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    List<TaskDto> list() {
        return taskService.list();
    }

    @GetMapping(params = "changes")
    List<TaskWithChangesDto> listWithChanges() {
        return taskService.listWithChanges();
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskDto> get(@PathVariable int id) {
        return taskService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<TaskDto> update(@PathVariable int id, @RequestBody TaskDto toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        toUpdate.setId(id);
        taskService.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<TaskDto> create(@RequestBody TaskDto toCreate) {
        TaskDto result = taskService.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TaskDto> delete(@PathVariable int id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
