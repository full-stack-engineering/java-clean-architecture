package io.github.mat3e.task;

import io.github.mat3e.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {
    @Bean
    TaskFacade taskFacade(final TaskRepository taskRepository, final DomainEventPublisher publisher) {
        return new TaskFacade(new TaskFactory(), taskRepository, publisher);
    }
}
