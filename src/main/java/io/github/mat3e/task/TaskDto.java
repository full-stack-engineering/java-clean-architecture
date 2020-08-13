package io.github.mat3e.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@JsonDeserialize(builder = TaskDto.Builder.class)
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

    private TaskDto(final Builder builder) {
        id = builder.id;
        description = builder.description;
        done = builder.done;
        deadline = builder.deadline;
        additionalComment = builder.additionalComment;
    }

    public Builder toBuilder() {
        return new Builder()
                .withId(id)
                .withDescription(description)
                .withDone(done)
                .withDeadline(deadline)
                .withAdditionalComment(additionalComment);
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

    @JsonPOJOBuilder
    public static class Builder {
        private int id;
        private String description;
        private boolean done;
        private ZonedDateTime deadline;
        private String additionalComment;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
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
            return new TaskDto(this);
        }
    }
}
