package io.github.mat3e.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;

@JsonDeserialize(as = ProjectStepDto.DeserializationImpl.class)
public interface ProjectStepDto {
    static ProjectStepDto create(final int id, final String description, final int daysToProjectDeadline) {
        return new DeserializationImpl(id, description, daysToProjectDeadline);
    }

    int getId();

    @NotNull String getDescription();

    int getDaysToProjectDeadline();

    class DeserializationImpl implements ProjectStepDto {
        private final int id;
        private final String description;
        private final int daysToProjectDeadline;

        DeserializationImpl(final int id, final String description, final int daysToProjectDeadline) {
            this.id = id;
            this.description = description;
            this.daysToProjectDeadline = daysToProjectDeadline;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public int getDaysToProjectDeadline() {
            return daysToProjectDeadline;
        }
    }
}
