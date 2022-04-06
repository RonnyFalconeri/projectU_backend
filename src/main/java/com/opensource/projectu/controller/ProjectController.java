package com.opensource.projectu.controller;

import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.api.ProjectsApi;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProjectController implements ProjectsApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> getProjectById(UUID id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> createProject(Project project) {
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Project> updateProject(UUID id, Project project) {
        return projectService.updateProject(id, project);
    }

    @Override
    public ResponseEntity<Void> deleteProject(UUID id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
