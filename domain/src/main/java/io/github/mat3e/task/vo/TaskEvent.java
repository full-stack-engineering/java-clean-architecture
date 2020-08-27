package io.github.mat3e.task.vo;

import io.github.mat3e.DomainEvent;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

public class TaskEvent implements DomainEvent {
    public enum State {
        DONE, UNDONE, UPDATED, DELETED, CREATED
    }

    private final Instant occurredOn;
    private final TaskSourceId id;
    private final State state;
    private final Data data;

    public TaskEvent(final TaskSourceId id, final State state) {
        this(id, state, null);
    }

    public TaskEvent(final TaskSourceId id, final State state, final Data data) {
        this.id = id;
        this.state = state;
        this.data = data;
        this.occurredOn = Instant.now();
    }

    @Override
    public Instant getOccurredOn() {
        return occurredOn;
    }

    public Optional<TaskSourceId> getSourceId() {
        return Optional.ofNullable(id);
    }

    public State getState() {
        return state;
    }

    public Optional<Data> getData() {
        return Optional.ofNullable(data);
    }

    public static class Data {
        private final String description;
        private final ZonedDateTime deadline;
        private final String additionalComment;

        public Data(final String description, final ZonedDateTime deadline, final String additionalComment) {
            this.description = description;
            this.deadline = deadline;
            this.additionalComment = additionalComment;
        }

        public String getDescription() {
            return description;
        }

        public ZonedDateTime getDeadline() {
            return deadline;
        }

        public String getAdditionalComment() {
            return additionalComment;
        }
    }
}
