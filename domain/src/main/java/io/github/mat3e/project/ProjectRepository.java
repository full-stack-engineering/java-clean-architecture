package io.github.mat3e.project;

import java.util.Optional;

interface ProjectRepository {
    <S extends Project> S save(S entity);

    Optional<Project> findById(Integer id);
}
