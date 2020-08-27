package io.github.mat3e.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

interface SqlProjectRepository extends Repository<ProjectSnapshot, Integer> {
    ProjectSnapshot save(ProjectSnapshot entity);

    Optional<ProjectSnapshot> findById(Integer id);

    @Query(
            value = "select distinct * from projects p join project_steps s on p.id = s.project_id where s.id = :id",
            nativeQuery = true
    )
    Optional<ProjectSnapshot> findWithNestedStepId(@Param("id") Integer id);
}

interface SqlProjectQueryRepository extends ProjectQueryRepository, Repository<ProjectSnapshot, Integer> {
}

interface SqlProjectStepRepository extends Repository<ProjectStepSnapshot, Integer> {
    void deleteById(int id);
}

@org.springframework.stereotype.Repository
class ProjectRepositoryImpl implements ProjectRepository {
    private final SqlProjectRepository repository;
    private final SqlProjectStepRepository stepRepository;

    ProjectRepositoryImpl(final SqlProjectRepository repository, final SqlProjectStepRepository stepRepository) {
        this.repository = repository;
        this.stepRepository = stepRepository;
    }

    @Override
    public Project save(final Project entity) {
        return Project.restore(repository.save(entity.getSnapshot()));
    }

    @Override
    public Optional<Project> findById(final Integer id) {
        return repository.findById(id).map(Project::restore);
    }

    @Override
    public Optional<Project> findByNestedStepId(final Integer id) {
        return repository.findWithNestedStepId(id).map(Project::restore);
    }

    @Override
    public void delete(final Project.Step entity) {
        stepRepository.deleteById(entity.getSnapshot().getId());
    }
}
