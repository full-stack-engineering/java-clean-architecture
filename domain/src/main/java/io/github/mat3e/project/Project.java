package io.github.mat3e.project;

import io.github.mat3e.task.vo.TaskCreator;
import io.github.mat3e.task.vo.TaskSourceId;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class Project {
    static Project restore(ProjectSnapshot snapshot) {
        return new Project(snapshot.getId(), snapshot.getName(), snapshot.getSteps());
    }

    private final int id;
    private final String name;
    private final Set<Step> steps = new HashSet<>();

    private Project(final int id, final String name, final Set<ProjectStepSnapshot> steps) {
        this.id = id;
        this.name = name;
        modifySteps(steps);
    }

    ProjectSnapshot getSnapshot() {
        return new ProjectSnapshot(id, name, steps.stream().map(Step::getSnapshot).collect(toSet()));
    }

    Set<TaskCreator> convertToTasks(final ZonedDateTime projectDeadline) {
        if (steps.stream().anyMatch(step -> step.hasCorrespondingTask && !step.correspondingTaskDone)) {
            throw new IllegalStateException("There are still some undone tasks from a previous project instance!");
        }
        Set<TaskCreator> result = steps.stream()
                .map(step -> new TaskCreator(
                                new TaskSourceId(String.valueOf(step.id)),
                                step.description,
                                projectDeadline.plusDays(step.daysToProjectDeadline)
                        )
                ).collect(toSet());
        // FIXME: we are not sure here if task was REALLY created; there should be a dedicated event for that
        steps.forEach(step -> {
            step.hasCorrespondingTask = true;
            step.correspondingTaskDone = false;
        });
        return result;
    }

    void updateStep(final int stepId, final boolean taskDone) {
        steps.stream()
                .filter(step -> step.id == stepId)
                .forEach(step -> step.correspondingTaskDone = taskDone);
    }

    Set<Step> modifySteps(final Set<ProjectStepSnapshot> stepSnapshots) {
        Set<Step> stepsToRemove = new HashSet<>();
        steps.forEach(existingStep -> stepSnapshots.stream()
                .filter(potentialOverride -> existingStep.id == potentialOverride.getId())
                .findFirst()
                .ifPresentOrElse(
                        overridingStep -> {
                            existingStep.description = overridingStep.getDescription();
                            existingStep.daysToProjectDeadline = overridingStep.getDaysToProjectDeadline();
                        },
                        () -> stepsToRemove.add(existingStep)
                )
        );
        stepsToRemove.forEach(this::removeStep);
        stepSnapshots.stream()
                .filter(newStep -> steps.stream()
                        .noneMatch(existingStep -> existingStep.id == newStep.getId())
                ).map(Step::restore)
                .collect(toSet())
                // collecting first to allow multiple id=0
                .forEach(this::addStep);
        // can be converted to internal domain event, e.g. removed ids
        return stepsToRemove;
    }

    private void addStep(Step step) {
        steps.add(step);
    }

    private void removeStep(Step step) {
        steps.remove(step);
    }

    static class Step {
        static Step restore(ProjectStepSnapshot snapshot) {
            return new Step(
                    snapshot.getId(),
                    snapshot.getDescription(),
                    snapshot.getDaysToProjectDeadline(),
                    snapshot.hasCorrespondingTask(),
                    snapshot.isCorrespondingTaskDone()
            );
        }

        private int id;
        private String description;
        private int daysToProjectDeadline;
        private boolean hasCorrespondingTask;
        private boolean correspondingTaskDone;

        private Step(final int id, final String description, final int daysToProjectDeadline, final boolean hasCorrespondingTask, final boolean correspondingTaskDone) {
            this.id = id;
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
            this.hasCorrespondingTask = hasCorrespondingTask;
            this.correspondingTaskDone = correspondingTaskDone;
        }

        ProjectStepSnapshot getSnapshot() {
            return new ProjectStepSnapshot(id, description, daysToProjectDeadline, hasCorrespondingTask, correspondingTaskDone);
        }
    }
}
