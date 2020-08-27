package io.github.mat3e.task.dto;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TaskDto {
    static public Builder builder() {
        return new Builder();
    }

    private final int id;
    @NotNull
    private final String description;
    private final boolean done;
    private final ZonedDateTime deadline;
    private final String additionalComment;

    public TaskDto(final int id, final @NotNull String description, final boolean done, final ZonedDateTime deadline, final String additionalComment) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
        this.additionalComment = additionalComment;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public TaskDto withId(final int id) {
        return new TaskDto(id, description, done, deadline, additionalComment);
    }

    public static class Builder {
        private int id;
        @NotNull
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(@NotNull String description) {
            this.description = description;
            return this;
        }

        public Builder withDone(boolean done) {
            this.done = done;
            return this;
        }

        public Builder withDeadline(ZonedDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder withAdditionalComment(String additionalComment) {
            this.additionalComment = additionalComment;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(id, description, done, deadline, additionalComment);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskDto)) return false;
        final TaskDto that = (TaskDto) o;
        return id == that.id &&
                done == that.done &&
                description.equals(that.description) &&
                Objects.equals(deadline, that.deadline) &&
                Objects.equals(additionalComment, that.additionalComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done, deadline, additionalComment);
    }
}
