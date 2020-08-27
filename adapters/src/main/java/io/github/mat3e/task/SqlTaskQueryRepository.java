package io.github.mat3e.task;

import org.springframework.data.repository.Repository;

interface SqlTaskQueryRepository extends TaskQueryRepository, Repository<SqlTask, Integer> {
}
