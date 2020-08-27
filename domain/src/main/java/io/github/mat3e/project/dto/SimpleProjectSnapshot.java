package io.github.mat3e.project.dto;

public class SimpleProjectSnapshot {
    private int id;
    private String name;

    public SimpleProjectSnapshot() {
    }

    public SimpleProjectSnapshot(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
