package io.github.mat3e.project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
class SqlProject {
    static SqlProject fromProject(Project source) {
        var result = new SqlProject();
        result.id = source.getId();
        result.name = source.getName();
        source.getSteps().stream()
                .map(step -> SqlProjectStep.fromStep(step, result))
                .forEach(result.steps::add);
        return result;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project", fetch = FetchType.EAGER)
    private final Set<SqlProjectStep> steps = new HashSet<>();

    public SqlProject() {
    }

    Project toProject() {
        var result = new Project();
        result.setId(id);
        result.setName(name);
        steps.forEach(sqlStep -> result.addStep(sqlStep.toStep(result)));
        return result;
    }
}
