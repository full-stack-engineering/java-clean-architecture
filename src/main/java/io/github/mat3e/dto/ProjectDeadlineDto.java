package io.github.mat3e.dto;

import java.time.ZonedDateTime;

public class ProjectDeadlineDto {
    private ZonedDateTime deadline;

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }
}
