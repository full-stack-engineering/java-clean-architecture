package io.github.mat3e.task.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public interface TaskWithChangesQueryDto {
    int getId();

    @NotNull
    String getDescription();

    boolean isDone();

    ZonedDateTime getDeadline();

    int getChangesCount();
}
