package io.github.mat3e.task;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class TaskWarmup implements ApplicationListener<ContextRefreshedEvent> {
    private final TaskInitializer initializer;

    TaskWarmup(final TaskRepository taskRepository, final TaskQueryRepository taskQueryRepository) {
        this.initializer = new TaskInitializer(taskRepository, taskQueryRepository);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        initializer.init();
    }
}
