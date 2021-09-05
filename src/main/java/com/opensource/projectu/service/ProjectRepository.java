package com.opensource.projectu.service;

import com.opensource.projectu.openapi.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
