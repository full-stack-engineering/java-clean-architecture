package io.github.mat3e.task;

import io.github.mat3e.project.dto.SimpleProject;

import java.time.ZonedDateTime;

class Task {
    static Task restore(TaskSnapshot snapshot) {
        return new Task(
                snapshot.getId(),
                snapshot.getDescription(),
                snapshot.getDone(),
                snapshot.getDeadline(),
                snapshot.getChangesCount(),
                snapshot.getAdditionalComment(),
                snapshot.getProject() != null ? SimpleProject.restore(snapshot.getProject()) : null
        );
    }

    private int id;
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    private final SimpleProject project;

    private Task(
            final int id,
            final String description,
            final boolean done,
            final ZonedDateTime deadline,
            final int changesCount,
            final String additionalComment,
            final SimpleProject project
    ) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.changesCount = changesCount;
        this.additionalComment = additionalComment;
        this.project = project;
    }

    TaskSnapshot getSnapshot() {
        return new TaskSnapshot(
                id,
                description,
                done,
                deadline,
                changesCount,
                additionalComment,
                project != null ? project.getSnapshot() : null
        );
    }

    void toggle() {
        done = !done;
        ++changesCount;
    }

    void updateInfo(String description, ZonedDateTime deadline, String additionalComment) {
        // rules, e.g. cannot be updated when done
        this.description = description;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }
}
