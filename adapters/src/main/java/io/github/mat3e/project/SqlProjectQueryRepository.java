package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<Project, Integer> {
}
