package io.github.mat3e.project;

import io.github.mat3e.project.dto.ProjectDto;

import java.util.Optional;
import java.util.Set;

public interface ProjectQueryRepository {
    Optional<ProjectDto> findDtoById(Integer id);

    <T> Set<T> findBy(Class<T> type);

    long count();
}
