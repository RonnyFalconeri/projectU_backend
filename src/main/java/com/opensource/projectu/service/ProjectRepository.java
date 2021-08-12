package com.opensource.projectu.service;

import com.opensource.projectu.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findProjectById(String id);
}
