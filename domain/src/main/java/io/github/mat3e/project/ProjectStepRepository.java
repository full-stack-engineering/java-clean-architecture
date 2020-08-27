package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface ProjectStepRepository extends Repository<ProjectStep, Integer> {
    void delete(ProjectStep entity);
}
