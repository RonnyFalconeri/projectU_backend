package com.opensource.projectu.controller;

import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.api.ProjectsApi;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController implements ProjectsApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> getProjectById(String id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> createProject(Project project) {
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Project> updateProject(String id, Project project) {
        return new ResponseEntity<>(projectService.updateProject(id, project), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteProject(String id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
