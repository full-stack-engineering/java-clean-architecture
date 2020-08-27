package io.github.mat3e.project;

import io.github.mat3e.project.dto.SimpleProject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
public class SqlSimpleProject {
    public static SqlSimpleProject fromProject(SimpleProject source) {
        var result = new SqlSimpleProject();
        result.id = source.getId();
        result.name = source.getName();
        return result;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;

    public SimpleProject toProject() {
        return new SimpleProject(id, name);
    }
}
