package io.github.mat3e.project;

import io.github.mat3e.project.query.SimpleProjectQueryDto;
import io.github.mat3e.task.TaskDto;
import io.github.mat3e.task.TaskFacade;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class ProjectFacade {
    private final ProjectRepository projectRepository;
    private final ProjectStepRepository projectStepRepository;
    private final TaskFacade taskFacade;

    ProjectFacade(ProjectRepository projectRepository, ProjectStepRepository projectStepRepository, TaskFacade taskFacade) {
        this.projectRepository = projectRepository;
        this.projectStepRepository = projectStepRepository;
        this.taskFacade = taskFacade;
    }

    Project save(Project toSave) {
        if (toSave.getId() != 0) {
            return saveWithId(toSave);
        }
        if (toSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        toSave.getSteps().forEach(step -> {
            if (step.getProject() == null) {
                step.setProject(toSave);
            }
        });
        return projectRepository.save(toSave);
    }

    private Project saveWithId(Project toSave) {
        return projectRepository.findById(toSave.getId()).map(existingProject -> {
            Set<ProjectStep> stepsToRemove = new HashSet<>();
            existingProject.setName(toSave.getName());
            existingProject.getSteps()
                    .forEach(existingStep -> toSave.getSteps().stream()
                            .filter(potentialOverride -> existingStep.getId() == potentialOverride.getId())
                            .findFirst()
                            .ifPresentOrElse(
                                    overridingStep -> {
                                        existingStep.setDescription(overridingStep.getDescription());
                                        existingStep.setDaysToProjectDeadline(overridingStep.getDaysToProjectDeadline());
                                    },
                                    () -> stepsToRemove.add(existingStep)
                            )
                    );
            stepsToRemove.forEach(toRemove -> {
                existingProject.removeStep(toRemove);
                projectStepRepository.delete(toRemove);
            });
            toSave.getSteps().stream()
                    .filter(newStep -> existingProject.getSteps().stream()
                            .noneMatch(existingStep -> existingStep.getId() == newStep.getId())
                    ).collect(toSet())
                    // collecting first to allow multiple id=0
                    .forEach(existingProject::addStep);
            projectRepository.save(existingProject);
            return existingProject;
        }).orElseGet(() -> {
            toSave.getSteps().forEach(step -> {
                if (step.getProject() == null) {
                    step.setProject(toSave);
                }
            });
            return projectRepository.save(toSave);
        });
    }

    List<Project> list() {
        return projectRepository.findAll();
    }

    Optional<Project> get(int id) {
        return projectRepository.findById(id);
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        if (taskFacade.areUndoneTasksWithProjectId(projectId)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        return projectRepository.findById(projectId).map(project -> {
            List<TaskDto> tasks = project.getSteps().stream()
                    .map(step -> TaskDto.builder()
                            .withDescription(step.getDescription())
                            .withDeadline(projectDeadline.plusDays(step.getDaysToProjectDeadline()))
                            .build()
                    ).collect(toList());
            return taskFacade.saveAll(tasks, new SimpleProjectQueryDto(projectId, project.getName()));
        }).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }
}
