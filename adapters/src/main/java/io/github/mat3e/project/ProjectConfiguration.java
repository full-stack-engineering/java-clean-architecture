package io.github.mat3e.project;

import io.github.mat3e.task.TaskFacade;
import io.github.mat3e.task.TaskQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProjectConfiguration {
    @Bean
    ProjectFacade projectFacade(
            ProjectRepository projectRepository,
            TaskFacade taskFacade,
            TaskQueryRepository taskQueryRepository
    ) {
        return new ProjectFacade(
                new ProjectFactory(),
                projectRepository,
                taskFacade,
                taskQueryRepository
        );
    }
}
