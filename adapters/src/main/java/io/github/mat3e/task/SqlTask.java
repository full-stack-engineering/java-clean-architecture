package io.github.mat3e.task;

import io.github.mat3e.project.SqlSimpleProject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tasks")
class SqlTask {
    static SqlTask fromTask(Task source) {
        var result = new SqlTask();
        result.id = source.getId();
        result.description = source.getDescription();
        result.done = source.isDone();
        result.deadline = source.getDeadline();
        result.changesCount = source.getChangesCount();
        result.additionalComment = source.getAdditionalComment();
        result.project = source.getProject() == null ? null : SqlSimpleProject.fromProject(source.getProject());
        return result;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @NotNull
    private String description;
    private boolean done;
    private ZonedDateTime deadline;
    private int changesCount;
    private String additionalComment;
    @ManyToOne
    @JoinColumn(name = "source_id")
    private SqlSimpleProject project;

    public SqlTask() {
    }

    Task toTask() {
        var result = new Task(description, deadline, project == null ? null : project.toProject());
        result.setId(id);
        result.setDone(done);
        result.setChangesCount(changesCount);
        result.setAdditionalComment(additionalComment);
        return result;
    }
}
