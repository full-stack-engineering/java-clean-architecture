package io.github.mat3e.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TaskConfiguration {
    @Bean
    TaskFacade taskFacade(TaskRepository taskRepository) {
        return new TaskFacade(new TaskFactory(), taskRepository);
    }
}
