package com.opensource.projectu.repository;

import com.opensource.projectu.openapi.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends MongoRepository<Project, UUID> {

    Optional<Project> findByTasksId(UUID id);
}
