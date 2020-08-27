package io.github.mat3e.project;

import java.util.Optional;

interface ProjectRepository {
    Project save(Project entity);

    Optional<Project> findById(Integer id);

    void delete(Project.Step entity);
}
