package io.github.mat3e.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = ProjectDto.DeserializationImpl.class)
public interface ProjectDto {
    static ProjectDto create(final int id, final String name, final List<ProjectStepDto> steps) {
        return new DeserializationImpl(id, name, steps);
    }

    int getId();

    String getName();

    List<ProjectStepDto> getSteps();

    class DeserializationImpl implements ProjectDto {
        private final int id;
        private final String name;
        private final List<ProjectStepDto> steps;

        DeserializationImpl(final int id, final String name, final List<ProjectStepDto> steps) {
            this.id = id;
            this.name = name;
            this.steps = steps;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<ProjectStepDto> getSteps() {
            return steps;
        }
    }
}
