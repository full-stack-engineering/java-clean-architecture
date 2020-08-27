package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;
import io.github.mat3e.project.dto.ProjectStepDto;
import io.github.mat3e.task.TaskFacade;
import io.github.mat3e.task.dto.TaskDto;
import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskEvent;
import io.github.mat3e.task.vo.TaskSourceId;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class ProjectFacade {
    private final ProjectFactory projectFactory;
    private final ProjectRepository projectRepository;
    private final TaskFacade taskFacade;

    ProjectFacade(final ProjectFactory projectFactory, final ProjectRepository projectRepository, final TaskFacade taskFacade) {
        this.projectFactory = projectFactory;
        this.projectRepository = projectRepository;
        this.taskFacade = taskFacade;
    }

    public void handle(TaskEvent event) {
        event.getSourceId()
                .map(TaskSourceId::getId)
                .map(Integer::parseInt)
                .ifPresent(stepId -> {
                            switch (event.getState()) {
                                case DONE:
                                case DELETED:
                                    updateStep(stepId, true);
                                    break;
                                case UNDONE:
                                    updateStep(stepId, false);
                                    break;
                                case UPDATED:
                                default:
                                    break;
                            }
                        }
                );
    }

    void updateStep(int stepId, boolean done) {
        projectRepository.findByNestedStepId(stepId)
                .ifPresent(project -> {
                    project.updateStep(stepId, done);
                    projectRepository.save(project);
                });
    }

    public ProjectDto save(ProjectDto dtoToSave) {
        if (dtoToSave.getId() != 0) {
            return toDto(saveWithId(dtoToSave));
        }
        if (dtoToSave.getSteps().stream().anyMatch(step -> step.getId() != 0)) {
            throw new IllegalStateException("Cannot add project with existing steps");
        }
        return toDto(projectRepository.save(projectFactory.from(dtoToSave)));
    }

    private Project saveWithId(ProjectDto dtoToSave) {
        return projectRepository.findById(dtoToSave.getId()).map(existingProject -> {
            Project toSave = projectFactory.from(dtoToSave);
            Set<Project.Step> removedSteps = existingProject.modifySteps(toSave.getSnapshot().getSteps());
            projectRepository.save(existingProject);
            removedSteps.forEach(projectRepository::delete);
            return existingProject;
        }).orElseGet(() -> projectRepository.save(projectFactory.from(dtoToSave)));
    }

    List<TaskDto> createTasks(int projectId, ZonedDateTime projectDeadline) {
        return projectRepository.findById(projectId).map(project -> {
            Set<TaskCreator> taskSources = project.convertToTasks(projectDeadline);
            projectRepository.save(project);
            return taskFacade.createTasks(taskSources);
        }).orElseThrow(() -> new IllegalArgumentException("No project found with id: " + projectId));
    }

    private ProjectDto toDto(Project project) {
        var snap = project.getSnapshot();
        return ProjectDto.create(snap.getId(), snap.getName(), snap.getSteps().stream().map(this::toDto).collect(toList()));
    }

    private ProjectStepDto toDto(ProjectStepSnapshot step) {
        return ProjectStepDto.create(step.getId(), step.getDescription(), step.getDaysToProjectDeadline());
    }
}
