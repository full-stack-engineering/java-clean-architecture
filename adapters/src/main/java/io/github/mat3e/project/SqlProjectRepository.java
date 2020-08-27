package io.github.mat3e.project;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface SqlProjectRepository extends Repository<SqlProject, Integer> {
    SqlProject save(SqlProject entity);

    Optional<SqlProject> findById(Integer id);
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<SqlProject, Integer> {
}

@org.springframework.stereotype.Repository
class ProjectRepositoryImpl implements ProjectRepository {
    private final SqlProjectRepository repository;

    ProjectRepositoryImpl(final SqlProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project save(final Project entity) {
        return repository.save(SqlProject.fromProject(entity)).toProject();
    }

    @Override
    public Optional<Project> findById(final Integer id) {
        return repository.findById(id).map(SqlProject::toProject);
    }
}
