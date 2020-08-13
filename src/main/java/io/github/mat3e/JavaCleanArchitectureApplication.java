package io.github.mat3e;

import io.github.mat3e.project.Project;
import io.github.mat3e.project.ProjectRepository;
import io.github.mat3e.project.ProjectStep;
import io.github.mat3e.task.Task;
import io.github.mat3e.task.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.ZonedDateTime;

@SpringBootApplication
public class JavaCleanArchitectureApplication implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(JavaCleanArchitectureApplication.class, args);
    }

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public JavaCleanArchitectureApplication(ProjectRepository projectRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (projectRepository.count() == 0) {
            var project = new Project();
            project.setName("Example project");
            project.addStep(new ProjectStep("First", -3, project));
            project.addStep(new ProjectStep("Second", -2, project));
            project.addStep(new ProjectStep("Third", 0, project));
            projectRepository.save(project);
        }
        if (taskRepository.count() == 0) {
            var task = new Task("Example task", ZonedDateTime.now(), null);
            taskRepository.save(task);
        }
    }
}
