package io.github.mat3e.project;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class ProjectWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final ProjectInitializer initializer;

    ProjectWarmup(final ProjectRepository projectRepository, final ProjectQueryRepository projectQueryRepository) {
        this.initializer = new ProjectInitializer(projectRepository, projectQueryRepository);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        initializer.init();
    }
}
