package io.github.mat3e.controller;

import io.github.mat3e.dto.ProjectDeadlineDto;
import io.github.mat3e.dto.TaskDto;
import io.github.mat3e.entity.Project;
import io.github.mat3e.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@RequestMapping("/projects")
class ProjectController {
    private final ProjectService projectService;

    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    List<Project> list() {
        return projectService.list();
    }

    @GetMapping("/{id}")
    ResponseEntity<Project> get(@PathVariable int id) {
        return projectService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Project> update(@PathVariable int id, @RequestBody Project toUpdate) {
        if (id != toUpdate.getId() && toUpdate.getId() != 0) {
            throw new IllegalStateException("Id in URL is different than in body: " + id + " and " + toUpdate.getId());
        }
        toUpdate.setId(id);
        projectService.save(toUpdate);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    ResponseEntity<Project> create(@RequestBody Project toCreate) {
        Project result = projectService.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PostMapping("/{id}/tasks")
    List<TaskDto> createTasks(@PathVariable int id, @RequestBody ProjectDeadlineDto deadlineDto) {
        return projectService.createTasks(id, deadlineDto.getDeadline());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleClientError(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
