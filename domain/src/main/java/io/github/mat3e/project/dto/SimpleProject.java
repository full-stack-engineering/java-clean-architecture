package io.github.mat3e.project.dto;

public class SimpleProject {
    private final int id;
    private final String name;

    public SimpleProject(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
