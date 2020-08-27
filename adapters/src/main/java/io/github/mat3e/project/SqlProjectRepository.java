package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

interface SqlProjectRepository extends ProjectRepository, Repository<Project, Integer> {
}
