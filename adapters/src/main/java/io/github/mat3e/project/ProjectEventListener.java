package io.github.mat3e.project;

import io.github.mat3e.task.vo.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ProjectEventListener {
    private final ProjectFacade facade;

    ProjectEventListener(final ProjectFacade facade) {
        this.facade = facade;
    }

    @EventListener
    // warning: must be synchronous in current design
    public void on(TaskEvent event) {
        facade.handle(event);
    }
}
