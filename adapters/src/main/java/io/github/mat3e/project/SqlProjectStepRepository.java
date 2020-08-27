package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface SqlProjectStepRepository extends ProjectStepRepository, Repository<ProjectStep, Integer> {
}
