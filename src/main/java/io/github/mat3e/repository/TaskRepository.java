package io.github.mat3e.repository;

import io.github.mat3e.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProject_Id(int id);
}
