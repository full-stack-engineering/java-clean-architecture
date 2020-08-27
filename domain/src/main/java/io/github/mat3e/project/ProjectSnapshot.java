package io.github.mat3e.project;

import java.util.HashSet;
import java.util.Set;

class ProjectSnapshot {
    private int id;
    private String name;
    private final Set<ProjectStepSnapshot> steps = new HashSet<>();

    public ProjectSnapshot() {
    }

    ProjectSnapshot(final int id, final String name, final Set<ProjectStepSnapshot> steps) {
        this.id = id;
        this.name = name;
        this.steps.addAll(steps);
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    Set<ProjectStepSnapshot> getSteps() {
        return this.steps;
    }
}
